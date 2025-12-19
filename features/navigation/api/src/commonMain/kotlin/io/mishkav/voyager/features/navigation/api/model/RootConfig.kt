package io.mishkav.voyager.features.navigation.api.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface RootConfig {

    @Serializable
    data object Auth : RootConfig

    @Serializable
    data object Main : RootConfig

    @Serializable
    data object Intro : RootConfig

    @Serializable
    data object Onboarding : RootConfig

    @Serializable
    data object CountryDetails : RootConfig

    @Serializable
    data object Location : RootConfig
}
