package io.mishka.voyager.core.repositories.countrydetails.impl.mappers

import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryOverviewEntity
import io.mishka.voyager.core.repositories.countrydetails.api.models.remote.CountryOverviewDTO

internal fun CountryOverviewDTO.toEntity(): CountryOverviewEntity = CountryOverviewEntity(
    id = id,
    countryId = countryId,
    body = body,
    wikipediaUrl = wikipediaUrl,
)
