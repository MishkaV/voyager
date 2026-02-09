package io.mishka.voyager.core.repositories.countrydetails.api.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CountryAiSuggestResponseDTO(
    @SerialName("text") val text: String
)
