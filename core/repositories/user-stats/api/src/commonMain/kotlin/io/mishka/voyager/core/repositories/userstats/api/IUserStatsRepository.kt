package io.mishka.voyager.core.repositories.userstats.api

import io.mishka.voyager.core.repositories.base.Syncable
import io.mishka.voyager.core.repositories.userstats.api.models.local.UserStatsEntity
import kotlinx.coroutines.flow.Flow

interface IUserStatsRepository : Syncable {

    fun getUserStats(): Flow<UserStatsEntity>
}
