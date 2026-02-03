package io.mishka.voyager.core.repositories.userpreferences.api

import io.mishka.voyager.core.repositories.base.Syncable
import io.mishka.voyager.core.repositories.userpreferences.api.models.local.PrefEntity
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository : Syncable {

    suspend fun getAllPrefs(forceUpdate: Boolean = false): Flow<List<PrefEntity>>

    suspend fun addUserPref(userId: String, prefId: String)
}
