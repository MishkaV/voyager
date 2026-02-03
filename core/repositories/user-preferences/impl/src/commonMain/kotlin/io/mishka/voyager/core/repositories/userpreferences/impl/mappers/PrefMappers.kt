package io.mishka.voyager.core.repositories.userpreferences.impl.mappers

import io.mishka.voyager.core.repositories.userpreferences.api.models.local.PrefEntity
import io.mishka.voyager.core.repositories.userpreferences.api.models.remote.PrefDTO

internal fun PrefDTO.toEntity(): PrefEntity = PrefEntity(
    id = id,
    title = title,
)

internal fun PrefEntity.toDTO(): PrefDTO = PrefDTO(
    id = id,
    title = title,
)
