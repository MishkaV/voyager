package io.mishkav.voyager.features.navigation.api.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface RootConfig {

    @Serializable
    data class Auth(
        val successNavigationConfig: RootConfig
    ) : RootConfig

    @Serializable
    data object Main : RootConfig

    @Serializable
    data object Intro : RootConfig

    @Serializable
    data class Onboarding(
        val successNavigationConfig: RootConfig
    ) : RootConfig

    @Serializable
    data object CountryDetails : RootConfig

    @Serializable
    data class Location(
        // TODO Refactor that
        val successNavigationConfig: RootConfig
    ) : RootConfig
}
