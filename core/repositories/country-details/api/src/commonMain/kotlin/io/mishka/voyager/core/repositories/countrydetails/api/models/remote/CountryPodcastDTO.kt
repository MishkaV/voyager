package io.mishka.voyager.core.repositories.countrydetails.api.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryPodcastDTO(
    @SerialName("id") val id: String,
    @SerialName("country_id") val countryId: String,
    @SerialName("audio_full_patch") val audioFullPatch: String,
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String,
    @SerialName("duration_sec") val durationSec: Int,
)
