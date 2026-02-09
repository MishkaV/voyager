package io.mishka.voyager.core.repositories.countrydetails.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.binding
import io.github.jan.supabase.postgrest.Postgrest
import io.mishka.voyager.core.repositories.base.BaseRepository
import io.mishka.voyager.core.repositories.countrydetails.api.ICountryOverviewRepository
import io.mishka.voyager.core.repositories.countrydetails.api.datasource.CountryOverviewDao
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryOverviewEntity
import io.mishka.voyager.core.repositories.countrydetails.api.models.remote.CountryOverviewDTO
import io.mishka.voyager.core.repositories.countrydetails.impl.mappers.toEntity

@ContributesBinding(
    scope = AppScope::class,
    binding = binding<ICountryOverviewRepository>(),
)
class CountryOverviewRepository(
    private val dao: Lazy<CountryOverviewDao>,
    private val supabasePostgrest: Postgrest,
) : ICountryOverviewRepository, BaseRepository() {

    override suspend fun getByCountryId(
        countryId: String,
    ): Result<CountryOverviewEntity?> {
        logger.d { "getByCountryId: countryId=$countryId" }

        return restartableLoad(
            forceUpdate = dao.value.getByCountryId(countryId) == null,
            remoteLoad = { force ->
                supabasePostgrest.from(TABLE_COUNTRY_OVERVIEW)
                    .select {
                        filter {
                            CountryOverviewDTO::countryId eq countryId
                        }
                    }
                    .decodeSingleOrNull<CountryOverviewDTO>()
            },
            localLoad = { force ->
                dao.value.getByCountryId(countryId)
            },
            replaceLocalData = { dto ->
                dto?.let {
                    val entity = it.toEntity()
                    dao.value.upsert(entity)
                    entity
                }
            }
        )
    }

    private companion object {
        const val TABLE_COUNTRY_OVERVIEW = "country_overview"
    }
}
