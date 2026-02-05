package io.mishka.voyager.core.repositories.userstats.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding
import io.github.jan.supabase.postgrest.Postgrest
import io.mishka.voyager.core.repositories.base.BaseRepository
import io.mishka.voyager.core.repositories.base.Syncable
import io.mishka.voyager.core.repositories.userstats.api.IUserStatsRepository
import io.mishka.voyager.core.repositories.userstats.api.datasource.UserStatsDao
import io.mishka.voyager.core.repositories.userstats.api.models.local.UserStatsEntity
import io.mishka.voyager.core.repositories.userstats.api.models.remote.UserStatsDTO
import io.mishka.voyager.core.repositories.userstats.impl.mappers.toEntity
import io.mishka.voyager.supabase.api.ISupabaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import java.util.logging.Logger

@ContributesBinding(
    scope = AppScope::class,
    binding = binding<IUserStatsRepository>(),
)
@ContributesIntoSet(
    scope = AppScope::class,
    binding = binding<Syncable>(),
)
class UserStatsRepository(
    private val userStatsDao: Lazy<UserStatsDao>,
    private val supabasePostgrest: Postgrest,
    private val supabaseAuth: ISupabaseAuth,
) : IUserStatsRepository, BaseRepository() {

    override fun getUserStats(): Flow<UserStatsEntity> {
        logger.d { "getUserStats()" }
        return userStatsDao.value.getStatsFlow()
            .onEach { logger.d { "KEK: new stats - $it" } }
            .mapNotNull { stats ->
                stats ?: supabaseAuth.getCurrentUser()?.id?.let {
                    UserStatsEntity(
                        userId = it
                    )
                }
            }
    }

    override suspend fun sync() {
        logger.i { "Sync user stats repository" }

        retryAction {
            supabaseAuth.getCurrentUser()?.id?.let { userId ->
                val stats = supabasePostgrest.from(TABLE_USER_TRAVEL_STATS)
                    .select {
                        filter {
                            UserStatsDTO::userId eq userId
                        }
                    }
                    .decodeSingleOrNull<UserStatsDTO>()

                stats?.let { dto ->
                    userStatsDao.value.upsert(dto.toEntity())
                } ?: userStatsDao.value.deleteAll()
            }
        }
    }

    private companion object {
        const val TABLE_USER_TRAVEL_STATS = "user_travel_stats"
    }
}
