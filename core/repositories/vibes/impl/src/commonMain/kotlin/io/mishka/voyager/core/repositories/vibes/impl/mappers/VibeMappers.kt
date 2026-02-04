package io.mishka.voyager.core.repositories.vibes.impl.mappers

import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeCategoryEntity
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeEntity
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibesCountryEntity
import io.mishka.voyager.core.repositories.vibes.api.models.remote.VibeCategoryDTO
import io.mishka.voyager.core.repositories.vibes.api.models.remote.VibeDTO
import io.mishka.voyager.core.repositories.vibes.api.models.remote.VibesCountryDTO

internal fun VibeCategoryDTO.toEntity(): VibeCategoryEntity = VibeCategoryEntity(
    id = id,
    title = title,
)

internal fun VibeDTO.toEntity(): VibeEntity = VibeEntity(
    id = id,
    categoryId = categoryId,
    title = title,
    iconEmoji = iconEmoji,
)

internal fun VibesCountryDTO.toEntity(): VibesCountryEntity = VibesCountryEntity(
    countryId = countryId,
    vibeId = vibeId,
)
