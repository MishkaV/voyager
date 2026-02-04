package io.mishka.voyager.core.repositories.userpreferences.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding
import io.github.jan.supabase.postgrest.Postgrest
import io.mishka.voyager.core.repositories.base.BaseRepository
import io.mishka.voyager.core.repositories.base.Syncable
import io.mishka.voyager.core.repositories.userpreferences.api.IUserPreferencesRepository
import io.mishka.voyager.core.repositories.userpreferences.api.datasource.PrefDao
import io.mishka.voyager.core.repositories.userpreferences.api.models.local.PrefEntity
import io.mishka.voyager.core.repositories.userpreferences.api.models.remote.PrefDTO
import io.mishka.voyager.core.repositories.userpreferences.api.models.remote.UserPrefDTO
import io.mishka.voyager.core.repositories.userpreferences.impl.mappers.toEntity
import io.mishka.voyager.supabase.api.ISupabaseAuth
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

@ContributesBinding(
    scope = AppScope::class,
    binding = binding<IUserPreferencesRepository>(),
)
@ContributesIntoSet(
    scope = AppScope::class,
    binding = binding<Syncable>(),
)
class UserPreferencesRepository(
    private val prefDao: Lazy<PrefDao>,
    private val supabasePostgrest: Postgrest,
    private val supabaseAuth: ISupabaseAuth,
) : IUserPreferencesRepository, BaseRepository() {

    override fun getAllPrefs(): Flow<List<PrefEntity>> {
        logger.d { "getAllPrefs()" }
        return prefDao.value.getAllPrefsFlow()
    }

    override suspend fun addUserPref(prefId: String) {
        logger.d { "Adding user pref: prefId=$prefId" }

        retryAction {
            supabaseAuth.getCurrentUser()?.id?.let { userId ->
                // Firstly clean all current
                supabasePostgrest.from(TABLE_USER_PREFS).delete {
                    filter {
                        UserPrefDTO::userId eq userId
                    }
                }

                // Update with new
                supabasePostgrest.from(TABLE_USER_PREFS).insert(
                    UserPrefDTO(
                        prefId = prefId,
                        userId = userId,
                    )
                )
            }
        }
    }

    override suspend fun addUserPrefs(prefIds: List<String>) {
        coroutineScope {
            prefIds.map { prefId ->
                async { addUserPref(prefId) }
            }.awaitAll()
        }
    }

    override suspend fun sync() {
        logger.i { "Sync user preferences repository" }

        retryAction {
            val prefs = supabasePostgrest.from(TABLE_PREFS)
                .select()
                .decodeList<PrefDTO>()
                .map { dto -> dto.toEntity() }

            prefDao.value.replaceAll(*prefs.toTypedArray())
        }
    }

    private companion object Companion {
        const val TABLE_PREFS = "prefs"
        const val TABLE_USER_PREFS = "prefs_user"
    }
}
