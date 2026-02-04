package io.mishka.voyager.core.repositories.userpreferences.api

import io.mishka.voyager.core.repositories.base.Syncable
import io.mishka.voyager.core.repositories.userpreferences.api.models.local.PrefEntity
import kotlinx.coroutines.flow.Flow

interface IUserPreferencesRepository : Syncable {

    fun getAllPrefs(): Flow<List<PrefEntity>>

    suspend fun addUserPref(prefId: String)

    suspend fun addUserPrefs(prefIds: List<String>)
}
