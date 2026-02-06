package io.mishka.voyager.core.repositories.countries.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding
import io.github.jan.supabase.postgrest.Postgrest
import io.mishka.voyager.core.repositories.base.BaseRepository
import io.mishka.voyager.core.repositories.base.Cleanable
import io.mishka.voyager.core.repositories.base.Syncable
import io.mishka.voyager.core.repositories.countries.api.IUserCountriesRepository
import io.mishka.voyager.core.repositories.countries.api.datasource.UserCountryDao
import io.mishka.voyager.core.repositories.countries.api.models.remote.UserCountryDTO
import io.mishka.voyager.core.repositories.countries.impl.mappers.toEntity
import io.mishka.voyager.core.repositories.userstats.api.IUserStatsRepository
import io.mishka.voyager.supabase.api.ISupabaseAuth

@ContributesBinding(
    scope = AppScope::class,
    binding = binding<IUserCountriesRepository>(),
)
@ContributesIntoSet(
    scope = AppScope::class,
    binding = binding<Syncable>(),
)
@ContributesIntoSet(
    scope = AppScope::class,
    binding = binding<Cleanable>(),
)
class UserCountriesRepository(
    private val userCountryDao: Lazy<UserCountryDao>,
    private val supabasePostgrest: Postgrest,
    private val supabaseAuth: ISupabaseAuth,
    private val userStatsRepository: IUserStatsRepository,
) : IUserCountriesRepository, BaseRepository() {

    override suspend fun addCountryToVisited(countryId: String) {
        logger.d { "addCountryToVisited: countryId=$countryId" }

        retryAction {
            supabaseAuth.getAsyncCurrentUser()?.id?.let { userId ->
                supabasePostgrest.from(TABLE_COUNTRIES_VISITED).upsert(
                    UserCountryDTO(userId = userId, countryId = countryId)
                )

                // Update local database
                userCountryDao.value.upsert(
                    UserCountryDTO(userId = userId, countryId = countryId).toEntity()
                )
            }
        }

        userStatsRepository.sync()
    }

    override suspend fun removeCountryFromVisited(countryId: String) {
        logger.d { "removeCountryFromVisited: countryId=$countryId" }

        retryAction {
            supabaseAuth.getAsyncCurrentUser()?.id?.let { userId ->
                supabasePostgrest.from(TABLE_COUNTRIES_VISITED).delete {
                    filter {
                        UserCountryDTO::userId eq userId
                        UserCountryDTO::countryId eq countryId
                    }
                }

                // Update local database
                userCountryDao.value.delete(userId, countryId)
            }
        }

        userStatsRepository.sync()
    }

    override suspend fun sync() {
        logger.i { "Sync user countries repository" }

        retryAction {
            supabaseAuth.getAsyncCurrentUser()?.id?.let { userId ->
                val visited = supabasePostgrest.from(TABLE_COUNTRIES_VISITED)
                    .select {
                        filter {
                            UserCountryDTO::userId eq userId
                        }
                    }
                    .decodeList<UserCountryDTO>()
                    .map { dto -> dto.toEntity() }

                userCountryDao.value.replaceAllForUser(userId, visited)
            }
        }
    }

    override suspend fun cleanup() {
        logger.i { "Cleanup user countries" }
        userCountryDao.value.deleteAll()
    }

    private companion object {
        const val TABLE_COUNTRIES_VISITED = "countries_visited"
    }
}
