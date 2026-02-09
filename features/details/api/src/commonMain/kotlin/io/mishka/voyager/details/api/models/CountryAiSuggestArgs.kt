package io.mishka.voyager.details.api.models

import kotlinx.serialization.Serializable

@Serializable
data class CountryAiSuggestArgs(
    val aiSuggestId: String,
    val backgroundHex: String,
    val countryId: String,
)
