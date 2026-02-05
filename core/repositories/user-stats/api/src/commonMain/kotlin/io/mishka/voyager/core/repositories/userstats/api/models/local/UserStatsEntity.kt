package io.mishka.voyager.core.repositories.userstats.api.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "user_travel_stats")
data class UserStatsEntity(
    @PrimaryKey
    val userId: String,
    val countriesVisited: Int = 0,
    val continentsVisited: Int = 0,
    val worldExploredPercent: Float = 0f,
)
