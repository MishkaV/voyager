package io.mishka.voyager.core.repositories.userstats.api.datasource

import androidx.room.Dao
import androidx.room.Query
import io.mishka.voyager.core.repositories.userstats.api.models.local.UserStatsEntity
import io.mishka.voyager.core.utils.room.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface UserStatsDao : BaseDao<UserStatsEntity> {

    @Query("SELECT * FROM user_travel_stats LIMIT 1")
    fun getStatsFlow(): Flow<UserStatsEntity?>

    @Query("DELETE FROM user_travel_stats")
    suspend fun deleteAll()
}
