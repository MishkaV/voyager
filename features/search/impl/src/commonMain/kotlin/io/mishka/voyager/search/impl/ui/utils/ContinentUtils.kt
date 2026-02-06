package io.mishka.voyager.search.impl.ui.utils

import androidx.compose.ui.graphics.Color
import io.mishka.voyager.core.repositories.countries.api.models.local.Continent
import org.jetbrains.compose.resources.DrawableResource
import voyager.features.search.impl.generated.resources.Res
import voyager.features.search.impl.generated.resources.africa
import voyager.features.search.impl.generated.resources.asia
import voyager.features.search.impl.generated.resources.europe
import voyager.features.search.impl.generated.resources.north_america
import voyager.features.search.impl.generated.resources.oceania
import voyager.features.search.impl.generated.resources.south_america

val Continent.itemBackground: Color
    get() = when (this) {
        Continent.EUROPE -> Color(0xFF4C6FAE)
        Continent.ASIA -> Color(0xFFB2A137)
        Continent.AFRICA -> Color(0xFF663737)
        Continent.OCEANIA -> Color(0xFF3C8F6B)
        Continent.NORTH_AMERICA -> Color(0xFF983030)
        Continent.SOUTH_AMERICA -> Color(0xFF985A30)
    }

val Continent.itemImageRes: DrawableResource
    get() = when (this) {
        Continent.EUROPE -> Res.drawable.europe
        Continent.ASIA -> Res.drawable.asia
        Continent.AFRICA -> Res.drawable.africa
        Continent.OCEANIA -> Res.drawable.oceania
        Continent.NORTH_AMERICA -> Res.drawable.north_america
        Continent.SOUTH_AMERICA -> Res.drawable.south_america
    }
