package io.mishka.voyager.core.repositories.countrydetails.impl.mappers

import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryAiSuggestEntity
import io.mishka.voyager.core.repositories.countrydetails.api.models.remote.CountryAiSuggestDTO

internal fun CountryAiSuggestDTO.toEntity(): CountryAiSuggestEntity = CountryAiSuggestEntity(
    id = id,
    countryId = countryId,
    suggestText = suggestText,
    prompt = prompt,
)
