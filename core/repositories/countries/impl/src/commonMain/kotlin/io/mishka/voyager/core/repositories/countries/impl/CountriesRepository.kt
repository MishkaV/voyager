package io.mishka.voyager.core.repositories.countries.impl

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding
import io.github.jan.supabase.postgrest.Postgrest
import io.mishka.voyager.core.repositories.base.BaseRepository
import io.mishka.voyager.core.repositories.base.Syncable
import io.mishka.voyager.core.repositories.countries.api.ICountriesRepository
import io.mishka.voyager.core.repositories.countries.api.datasource.CountryDao
import io.mishka.voyager.core.repositories.countries.api.models.local.Continent
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryWithVisitedStatus
import io.mishka.voyager.core.repositories.countries.api.models.remote.CountryDTO
import io.mishka.voyager.core.repositories.countries.impl.mappers.toEntity
import io.mishka.voyager.core.repositories.paging.DefaultPagingConfig
import io.mishka.voyager.supabase.api.ISupabaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

@ContributesBinding(
    scope = AppScope::class,
    binding = binding<ICountriesRepository>(),
)
@ContributesIntoSet(
    scope = AppScope::class,
    binding = binding<Syncable>(),
)
class CountriesRepository(
    private val countryDao: Lazy<CountryDao>,
    private val supabasePostgrest: Postgrest,
    private val supabaseAuth: ISupabaseAuth,
) : ICountriesRepository, BaseRepository() {

    override fun getCountriesWithVisitedStatus(
        continent: Continent?,
        query: String?
    ): Flow<PagingData<CountryWithVisitedStatus>> {
        logger.d { "getCountriesWithVisitedStatus: continent=$continent, query=$query" }
        val userId = supabaseAuth.getCurrentUser()?.id ?: ""
        val queryPattern = query?.trim()?.takeIf { it.isNotEmpty() }?.let { "$it%" }

        return Pager(
            config = DefaultPagingConfig.create(),
            pagingSourceFactory = {
                countryDao.value.getPagingSourceWithVisitedStatus(
                    userId = userId,
                    continent = continent?.displayName,
                    queryPattern = queryPattern
                )
            },
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainModel() }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCountryWithVisitedStatus(countryId: String): Flow<CountryWithVisitedStatus> {
        logger.d { "getCountryWithVisitedStatus: countryId=$countryId" }

        return supabaseAuth.userFlow
            .filterNotNull()
            .flatMapLatest { userInfo ->
                countryDao.value.getCountryWithVisitedStatus(
                    userId = userInfo.id,
                    countryId = countryId
                ).map { it?.toDomainModel() }
            }
            .filterNotNull()
    }

    override suspend fun sync() {
        logger.i { "Sync countries repository" }

        retryAction {
            val countries = supabasePostgrest.from(TABLE_COUNTRIES)
                .select()
                .decodeList<CountryDTO>()
                .map { dto -> dto.toEntity() }

            countryDao.value.replaceAll(*countries.toTypedArray())
        }
    }

    private companion object {
        const val TABLE_COUNTRIES = "countries"
    }
}
