package io.mishka.voyager.onboarding.api

import com.arkivanov.decompose.ComponentContext
import io.mishkav.voyager.core.ui.decompose.CompositeDecomposeComponent

abstract class OnboardingComponent<C : Any> : CompositeDecomposeComponent<C>() {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
        ): OnboardingComponent<*>
    }
}
