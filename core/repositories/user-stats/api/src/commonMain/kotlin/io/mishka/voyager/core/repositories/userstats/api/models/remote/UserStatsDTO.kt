package io.mishka.voyager.core.repositories.userstats.api.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserStatsDTO(
    @SerialName("user_id")
    val userId: String,
    @SerialName("countries_visited")
    val countriesVisited: Long?,
    @SerialName("continents_visited")
    val continentsVisited: Long?,
    @SerialName("world_explored_percent")
    val worldExploredPercent: Double?,
)
