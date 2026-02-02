package io.mishkav.voyager.features.navigation.api.model

sealed interface VoyagerStartupStatus {

    object Loading : VoyagerStartupStatus

    object ShouldShowIntro : VoyagerStartupStatus

    object ShouldShowOnboarding : VoyagerStartupStatus

    object Main : VoyagerStartupStatus
}
