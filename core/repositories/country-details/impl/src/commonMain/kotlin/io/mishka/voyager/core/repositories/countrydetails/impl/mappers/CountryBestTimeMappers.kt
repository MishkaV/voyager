package io.mishka.voyager.core.repositories.countrydetails.impl.mappers

import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryBestTimeEntity
import io.mishka.voyager.core.repositories.countrydetails.api.models.remote.CountryBestTimeDTO

internal fun CountryBestTimeDTO.toEntity(): CountryBestTimeEntity = CountryBestTimeEntity(
    id = id,
    countryId = countryId,
    title = title,
    description = description,
)
