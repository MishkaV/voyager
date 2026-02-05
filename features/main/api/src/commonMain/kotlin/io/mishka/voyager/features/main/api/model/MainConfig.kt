package io.mishka.voyager.features.main.api.model

import kotlinx.serialization.Serializable

@Serializable
sealed class MainConfig {
    @Serializable
    data object Home : MainConfig()

    @Serializable
    data object Search : MainConfig()

    @Serializable
    data object Profile : MainConfig()
}
