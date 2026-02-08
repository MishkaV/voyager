package io.mishka.voyager.core.repositories.countrydetails.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.binding
import io.github.jan.supabase.functions.Functions
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.client.call.body
import io.mishka.voyager.core.repositories.base.BaseRepository
import io.mishka.voyager.core.repositories.countrydetails.api.ICountryAiSuggestsRepository
import io.mishka.voyager.core.repositories.countrydetails.api.datasource.CountryAiSuggestDao
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryAiSuggestEntity
import io.mishka.voyager.core.repositories.countrydetails.api.models.remote.CountryAiSuggestDTO
import io.mishka.voyager.core.repositories.countrydetails.impl.mappers.toEntity
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@ContributesBinding(
    scope = AppScope::class,
    binding = binding<ICountryAiSuggestsRepository>(),
)
class CountryAiSuggestsRepository(
    private val dao: Lazy<CountryAiSuggestDao>,
    private val supabasePostgrest: Postgrest,
    private val supabaseFunctions: Functions,
) : ICountryAiSuggestsRepository, BaseRepository() {

    override suspend fun getByCountryId(
        countryId: String,
        forceUpdate: Boolean
    ): Result<List<CountryAiSuggestEntity>> {
        logger.d { "getByCountryId: countryId=$countryId, forceUpdate=$forceUpdate" }

        return restartableLoad(
            forceUpdate = forceUpdate,
            remoteLoad = { force ->
                supabasePostgrest.from(TABLE_COUNTRY_AI_SUGGESTS)
                    .select {
                        filter {
                            CountryAiSuggestDTO::countryId eq countryId
                        }
                    }
                    .decodeList<CountryAiSuggestDTO>()
            },
            localLoad = { force ->
                dao.value.getByCountryId(countryId)
            },
            replaceLocalData = { dtos ->
                val entities = dtos.map { it.toEntity() }
                dao.value.upsert(*entities.toTypedArray())
                entities
            }
        )
    }

    override suspend fun generateAiSuggest(
        aiSuggestId: String,
        countryId: String
    ): Result<String> {
        logger.d { "generateAiSuggest: aiSuggestId=$aiSuggestId, countryId=$countryId" }

        return retryAction {
            val params = buildJsonObject {
                put("aiSuggestId", aiSuggestId)
                put("countryId", countryId)
            }

            val response = supabaseFunctions.invoke(
                function = FUNCTION_GENERATE_AI_SUGGEST,
                body = params
            )

            response.body<String>()
        }
    }

    private companion object {
        const val TABLE_COUNTRY_AI_SUGGESTS = "country_ai_suggests"
        const val FUNCTION_GENERATE_AI_SUGGEST = "generate-ai-suggest"
    }
}
