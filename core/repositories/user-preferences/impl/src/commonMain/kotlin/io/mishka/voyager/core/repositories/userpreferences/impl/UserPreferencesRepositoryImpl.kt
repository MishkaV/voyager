package io.mishka.voyager.core.repositories.userpreferences.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding
import io.github.jan.supabase.postgrest.Postgrest
import io.mishka.voyager.core.repositories.base.BaseRepository
import io.mishka.voyager.core.repositories.base.Syncable
import io.mishka.voyager.core.repositories.userpreferences.api.UserPreferencesRepository
import io.mishka.voyager.core.repositories.userpreferences.api.datasource.PrefDao
import io.mishka.voyager.core.repositories.userpreferences.api.models.local.PrefEntity
import io.mishka.voyager.core.repositories.userpreferences.api.models.remote.PrefDTO
import io.mishka.voyager.core.repositories.userpreferences.api.models.remote.UserPrefDTO
import io.mishka.voyager.core.repositories.userpreferences.impl.mappers.toEntity
import kotlinx.coroutines.flow.Flow

@ContributesBinding(
    scope = AppScope::class,
    binding = binding<UserPreferencesRepository>(),
)
@ContributesIntoSet(
    scope = AppScope::class,
    binding = binding<Syncable>(),
)
class UserPreferencesRepositoryImpl(
    private val prefDao: Lazy<PrefDao>,
    private val supabasePostgrest: Postgrest,
) : UserPreferencesRepository, BaseRepository() {

    override suspend fun getAllPrefs(forceUpdate: Boolean): Flow<List<PrefEntity>> {
        logger.d { "getAllPrefs()" }
        if (forceUpdate) {
            sync()
        }

        return prefDao.value.getAllPrefsFlow()
    }

    override suspend fun addUserPref(userId: String, prefId: String) {
        logger.d { "Adding user pref: userId=$userId, prefId=$prefId" }

        retryAction {
            supabasePostgrest.from(TABLE_USER_PREFS).insert(
                UserPrefDTO(
                    prefId = prefId,
                    userId = userId,
                )
            )
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

            prefDao.value.replaceAll(*prefs.toTypedArray())
        }
    }

    private companion object {
        const val TABLE_PREFS = "prefs"
        const val TABLE_USER_PREFS = "prefs_user"
    }
}
