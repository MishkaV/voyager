package io.mishka.voyager.core.repositories.vibes.api.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VibesCountryDTO(
    @SerialName("country_id")
    val countryId: String,
    @SerialName("vibe_id")
    val vibeId: String,
)
