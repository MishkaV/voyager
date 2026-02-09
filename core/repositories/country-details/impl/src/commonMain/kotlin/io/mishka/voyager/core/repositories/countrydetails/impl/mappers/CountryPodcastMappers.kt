package io.mishka.voyager.core.repositories.countrydetails.impl.mappers

import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryPodcastEntity
import io.mishka.voyager.core.repositories.countrydetails.api.models.remote.CountryPodcastDTO

internal fun CountryPodcastDTO.toEntity(): CountryPodcastEntity = CountryPodcastEntity(
    id = id,
    countryId = countryId,
    audioFullPatch = audioFullPatch,
    title = title,
    subtitle = subtitle,
    durationSec = durationSec,
)
