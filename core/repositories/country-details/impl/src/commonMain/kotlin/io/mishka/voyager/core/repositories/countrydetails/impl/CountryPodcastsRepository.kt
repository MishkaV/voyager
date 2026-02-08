package io.mishka.voyager.core.repositories.countrydetails.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.binding
import io.github.jan.supabase.postgrest.Postgrest
import io.mishka.voyager.core.repositories.base.BaseRepository
import io.mishka.voyager.core.repositories.countrydetails.api.ICountryPodcastsRepository
import io.mishka.voyager.core.repositories.countrydetails.api.datasource.CountryPodcastDao
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryPodcastEntity
import io.mishka.voyager.core.repositories.countrydetails.api.models.remote.CountryPodcastDTO
import io.mishka.voyager.core.repositories.countrydetails.impl.mappers.toEntity

@ContributesBinding(
    scope = AppScope::class,
    binding = binding<ICountryPodcastsRepository>(),
)
class CountryPodcastsRepository(
    private val dao: Lazy<CountryPodcastDao>,
    private val supabasePostgrest: Postgrest,
) : ICountryPodcastsRepository, BaseRepository() {

    override suspend fun getByCountryId(
        countryId: String,
    ): Result<List<CountryPodcastEntity>> {
        logger.d { "getByCountryId: countryId=$countryId" }

        return restartableLoad(
            forceUpdate = dao.value.getCountByCountryId(countryId) == 0,
            remoteLoad = { force ->
                supabasePostgrest.from(TABLE_COUNTRY_PODCASTS)
                    .select {
                        filter {
                            CountryPodcastDTO::countryId eq countryId
                        }
                    }
                    .decodeList<CountryPodcastDTO>()
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
        const val TABLE_COUNTRY_PODCASTS = "country_podcasts"
    }
}
