package io.mishka.voyager.home.impl.ui.models

import androidx.compose.runtime.Immutable
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryEntity
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeEntity

@Immutable
data class CountryUIModel(
    val country: CountryEntity,
    val isVisited: Boolean,
    val vibes: List<VibeEntity>,
)
