package io.mishka.voyager.core.repositories.countrydetails.api.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryAiSuggestDTO(
    @SerialName("id") val id: String,
    @SerialName("country_id") val countryId: String,
    @SerialName("suggest_text") val suggestText: String,
    @SerialName("prompt") val prompt: String,
)
