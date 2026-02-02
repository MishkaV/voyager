package io.mishka.voyager.onboarding.api.model

import kotlinx.serialization.Serializable

@Serializable
sealed class OnboardingConfig {
    @Serializable
    data object UserPrefs : OnboardingConfig()

    @Serializable
    data object Vibes : OnboardingConfig()
}
