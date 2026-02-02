package io.mishkav.voyager.features.navigation.api.model

sealed interface VoyagerStartupStatus {

    object Loading : VoyagerStartupStatus

    object ShouldShowIntro : VoyagerStartupStatus

    object Main : VoyagerStartupStatus
}
