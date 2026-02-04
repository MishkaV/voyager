package io.mishka.voyager.onboarding.impl.api

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.binding
import io.mishka.voyager.onboarding.api.OnboardingComponent
import io.mishka.voyager.onboarding.api.model.OnboardingConfig
import io.mishkav.voyager.core.ui.decompose.DecomposeComponent

@AssistedInject
class OnboardingComponentImpl(
    @Assisted componentContext: ComponentContext,
    private val userPrefsComponentFactory: UserPrefsDecomposeComponent.Factory,
    private val vibesComponentFactory: VibesDecomposeComponent.Factory,
) : OnboardingComponent<OnboardingConfig>(),
    ComponentContext by componentContext,
    BackHandlerOwner {

    override val stack: Value<ChildStack<OnboardingConfig, DecomposeComponent>> = childStack(
        source = navigation,
        serializer = OnboardingConfig.serializer(),
        initialConfiguration = OnboardingConfig.UserPrefs,
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(
        config: OnboardingConfig,
        componentContext: ComponentContext
    ): DecomposeComponent = when (config) {
        is OnboardingConfig.UserPrefs -> userPrefsComponentFactory.create(
            componentContext = componentContext,
            navigateToVibes = {
                navigation.pushNew(OnboardingConfig.Vibes)
            },
            navigateBack = navigation::pop,
        )

        is OnboardingConfig.Vibes -> vibesComponentFactory.create(
            componentContext = componentContext,
            navigateBack = navigation::pop,
        )
    }

    @AssistedFactory
    @ContributesBinding(AppScope::class, binding<OnboardingComponent.Factory>())
    interface Factory : OnboardingComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
        ): OnboardingComponentImpl
    }
}
