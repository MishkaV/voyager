package io.mishka.voyager.core.repositories.userstats.impl.mappers

import io.mishka.voyager.core.repositories.userstats.api.models.local.UserStatsEntity
import io.mishka.voyager.core.repositories.userstats.api.models.remote.UserStatsDTO

internal fun UserStatsDTO.toEntity(): UserStatsEntity = UserStatsEntity(
    userId = userId,
    countriesVisited = countriesVisited?.toInt() ?: 0,
    continentsVisited = continentsVisited?.toInt() ?: 0,
    worldExploredPercent = worldExploredPercent?.toFloat() ?: 0f,
)
