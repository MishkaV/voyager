package io.mishka.voyager.core.repositories.countrydetails.api.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryBestTimeDTO(
    @SerialName("id") val id: String,
    @SerialName("country_id") val countryId: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
)
