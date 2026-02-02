package io.mishkav.voyager.features.navigation.api

import androidx.compose.runtime.staticCompositionLocalOf
import io.mishkav.voyager.features.navigation.api.model.RootConfig

val LocalRootNavigation = staticCompositionLocalOf<RootNavigationInterface> {
    error("CompositionLocal LocalRootComponent not present")
}

interface RootNavigationInterface {
    fun push(config: RootConfig)

    fun replaceCurrent(config: RootConfig)
}
