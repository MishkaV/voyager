package io.mishka.voyager.core.repositories.countries.api.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryDTO(
    @SerialName("id") val id: String,
    @SerialName("iso2") val iso2: String,
    @SerialName("name") val name: String,
    @SerialName("capital") val capital: String,
    @SerialName("continent") val continent: String,
    @SerialName("primary_language") val primaryLanguage: String,
    @SerialName("primary_language_code") val primaryLanguageCode: String,
    @SerialName("primary_currency") val primaryCurrency: String,
    @SerialName("primary_currency_code") val primaryCurrencyCode: String,
    @SerialName("flag_full_patch") val flagFullPatch: String,
    @SerialName("background_hex") val backgroundHex: String,
)
