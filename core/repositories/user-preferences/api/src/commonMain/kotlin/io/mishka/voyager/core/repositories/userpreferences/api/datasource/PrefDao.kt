package io.mishka.voyager.core.repositories.userpreferences.api.datasource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.mishka.voyager.core.repositories.userpreferences.api.models.local.PrefEntity
import io.mishka.voyager.core.utils.room.BaseDao
import kotlinx.coroutines.flow.Flow

/**
 * DAO for accessing preference data from local Room database.
 */
@Dao
interface PrefDao : BaseDao<PrefEntity> {

    @Query("SELECT * FROM prefs")
    fun getAllPrefsFlow(): Flow<List<PrefEntity>>

    @Query("DELETE FROM prefs")
    suspend fun deleteAll()

    @Transaction
    suspend fun replaceAll(vararg obj: PrefEntity) {
        deleteAll()
        insert(*obj)
    }
}
