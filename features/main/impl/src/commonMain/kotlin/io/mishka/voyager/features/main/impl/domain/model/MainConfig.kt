package io.mishka.voyager.features.main.impl.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface MainConfig {
    val tab: MainBottomTab

    @Serializable
    data object Home : MainConfig {
        override val tab: MainBottomTab = MainBottomTab.HOME
    }

    @Serializable
    data object Search : MainConfig {
        override val tab: MainBottomTab = MainBottomTab.SEARCH
    }

    @Serializable
    data object Profile : MainConfig {
        override val tab: MainBottomTab = MainBottomTab.PROFILE
    }
}
