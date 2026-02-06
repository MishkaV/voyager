package io.mishka.voyager.core.repositories.countries.api.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCountryDTO(
    @SerialName("user_id") val userId: String,
    @SerialName("country_id") val countryId: String,
)
