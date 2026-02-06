package io.mishka.voyager.core.repositories.countries.impl.mappers

import io.mishka.voyager.core.repositories.countries.api.models.local.CountryEntity
import io.mishka.voyager.core.repositories.countries.api.models.local.UserCountryEntity
import io.mishka.voyager.core.repositories.countries.api.models.remote.CountryDTO
import io.mishka.voyager.core.repositories.countries.api.models.remote.UserCountryDTO

internal fun CountryDTO.toEntity(): CountryEntity = CountryEntity(
    id = id,
    iso2 = iso2,
    name = name,
    capital = capital,
    continent = continent,
    primaryLanguage = primaryLanguage,
    primaryLanguageCode = primaryLanguageCode,
    primaryCurrency = primaryCurrency,
    primaryCurrencyCode = primaryCurrencyCode,
    flagFullPatch = flagFullPatch,
    backgroundHex = backgroundHex,
)

internal fun UserCountryDTO.toEntity(): UserCountryEntity = UserCountryEntity(
    userId = userId,
    countryId = countryId,
)
