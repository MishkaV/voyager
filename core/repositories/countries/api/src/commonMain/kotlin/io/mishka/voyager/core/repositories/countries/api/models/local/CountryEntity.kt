package io.mishka.voyager.core.repositories.countries.api.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey val id: String,
    val iso2: String,
    val name: String,
    val capital: String,
    val continent: String,
    val primaryLanguage: String,
    val primaryLanguageCode: String,
    val primaryCurrency: String,
    val primaryCurrencyCode: String,
    val flagFullPatch: String,
    val backgroundHex: String,
)
