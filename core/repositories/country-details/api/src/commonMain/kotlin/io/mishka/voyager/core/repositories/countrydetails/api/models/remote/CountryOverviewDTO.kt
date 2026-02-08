package io.mishka.voyager.core.repositories.countrydetails.api.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryOverviewDTO(
    @SerialName("id") val id: String,
    @SerialName("country_id") val countryId: String,
    @SerialName("body") val body: String,
    @SerialName("wikipedia_url") val wikipediaUrl: String,
)
