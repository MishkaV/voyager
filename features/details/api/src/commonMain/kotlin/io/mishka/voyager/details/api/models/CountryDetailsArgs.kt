package io.mishka.voyager.details.api.models

import kotlinx.serialization.Serializable

@Serializable
data class CountryDetailsArgs(
    val countryId: String,
    val name: String,
    val flagFullPatch: String,
    val backgroundHex: String,
)
