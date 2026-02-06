package io.mishka.voyager.search.impl.ui.utils

import io.mishka.voyager.core.repositories.countries.api.models.local.CountryEntity
import io.mishkav.voyager.features.navigation.api.model.RootConfig

fun CountryEntity.toRootCountryConfig(): RootConfig.CountryDetails {
    return RootConfig.CountryDetails(
        countryId = id,
        name = name,
        flagFullPatch = flagFullPatch,
        backgroundHex = backgroundHex,
    )
}
