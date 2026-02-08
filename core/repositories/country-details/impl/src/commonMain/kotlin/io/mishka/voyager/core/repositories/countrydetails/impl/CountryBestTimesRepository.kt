package io.mishka.voyager.core.repositories.countrydetails.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.binding
import io.github.jan.supabase.postgrest.Postgrest
import io.mishka.voyager.core.repositories.base.BaseRepository
import io.mishka.voyager.core.repositories.countrydetails.api.ICountryBestTimesRepository
import io.mishka.voyager.core.repositories.countrydetails.api.datasource.CountryBestTimeDao
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryBestTimeEntity
import io.mishka.voyager.core.repositories.countrydetails.api.models.remote.CountryBestTimeDTO
import io.mishka.voyager.core.repositories.countrydetails.impl.mappers.toEntity

@ContributesBinding(
    scope = AppScope::class,
    binding = binding<ICountryBestTimesRepository>(),
)
class CountryBestTimesRepository(
    private val dao: Lazy<CountryBestTimeDao>,
    private val supabasePostgrest: Postgrest,
) : ICountryBestTimesRepository, BaseRepository() {

    override suspend fun getByCountryId(
        countryId: String,
        forceUpdate: Boolean
    ): Result<List<CountryBestTimeEntity>> {
        logger.d { "getByCountryId: countryId=$countryId, forceUpdate=$forceUpdate" }

        return restartableLoad(
            forceUpdate = forceUpdate,
            remoteLoad = { force ->
                supabasePostgrest.from(TABLE_COUNTRY_BEST_TIMES)
                    .select {
                        filter {
                            CountryBestTimeDTO::countryId eq countryId
                        }
                    }
                    .decodeList<CountryBestTimeDTO>()
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

    private companion object {
        const val TABLE_COUNTRY_BEST_TIMES = "country_best_times"
    }
}
