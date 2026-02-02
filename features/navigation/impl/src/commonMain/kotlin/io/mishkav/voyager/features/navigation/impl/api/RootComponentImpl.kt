package io.mishkav.voyager.features.navigation.impl.api

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.backhandler.BackHandler
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.binding
import io.mishka.voyager.auth.api.AuthComponent
import io.mishka.voyager.intro.api.IntroComponent
import io.mishka.voyager.onboarding.api.OnboardingComponent
import io.mishkav.voyager.core.ui.decompose.DecomposeComponent
import io.mishkav.voyager.core.ui.decompose.back.backAnimation
import io.mishkav.voyager.core.ui.uikit.transition.LocalNavAnimatedVisibilityScope
import io.mishkav.voyager.core.ui.uikit.transition.LocalSharedTransitionScope
import io.mishkav.voyager.features.navigation.api.RootComponent
import io.mishkav.voyager.features.navigation.api.model.RootConfig
import io.mishkav.voyager.features.navigation.api.model.VoyagerStartupStatus

@AssistedInject
class RootComponentImpl(
    @Assisted componentContext: ComponentContext,
    @Assisted externalBackHandler: BackHandler?,
    @Assisted startupStatus: VoyagerStartupStatus,
    private val authComponentFactory: AuthComponent.Factory,
    private val introComponentFactory: IntroComponent.Factory,
    private val onboardingComponentFactory: OnboardingComponent.Factory,
) : RootComponent, ComponentContext by componentContext, BackHandlerOwner {

    override val backHandler: BackHandler = externalBackHandler ?: BackDispatcher()

    private val navigation = StackNavigation<RootConfig>()

    private val stack: Value<ChildStack<RootConfig, DecomposeComponent>> = childStack(
        source = navigation,
        serializer = RootConfig.serializer(),
        initialConfiguration = when (startupStatus) {
            VoyagerStartupStatus.Main -> RootConfig.Main
            VoyagerStartupStatus.ShouldShowIntro -> RootConfig.Intro
            VoyagerStartupStatus.ShouldShowOnboarding -> RootConfig.Onboarding(
                successNavigationConfig = RootConfig.Main
            )
            VoyagerStartupStatus.Loading -> error("Not supported status")
        },
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(
        config: RootConfig,
        componentContext: ComponentContext
    ): DecomposeComponent = when (config) {
        is RootConfig.Main -> TODO("Add screen implementation")
        is RootConfig.Auth -> authComponentFactory.create(
            componentContext = componentContext,
            successNavigationConfig = config.successNavigationConfig,
        )

        is RootConfig.CountryDetails -> TODO("Add screen implementation")
        is RootConfig.Onboarding -> onboardingComponentFactory.create(
            componentContext = componentContext,
            successNavigationConfig = config.successNavigationConfig,
        )
        is RootConfig.Intro -> introComponentFactory.create(
            componentContext = componentContext,
        )

        is RootConfig.Location -> TODO("Add screen implementation")
    }

    @OptIn(ExperimentalDecomposeApi::class, ExperimentalSharedTransitionApi::class)
    @Composable
    override fun Render(modifier: Modifier) {
        val childStack by stack.subscribeAsState()

        SharedTransitionLayout {
            CompositionLocalProvider(
                LocalSharedTransitionScope provides this@SharedTransitionLayout
            ) {
                ChildStack(
                    modifier = modifier,
                    stack = childStack,
                    animation = backAnimation(
                        backHandler = backHandler,
                        onBack = ::goBack,
                    ),
                ) {
                    CompositionLocalProvider(
                        LocalNavAnimatedVisibilityScope provides this
                    ) {
                        it.instance.Render()
                    }
                }
            }
        }
    }

    override fun goBack() {
        navigation.pop()
    }

    override fun push(config: RootConfig) {
        navigation.pushNew(config)
    }

    override fun replaceCurrent(config: RootConfig) {
        navigation.replaceCurrent(config)
    }

    @AssistedFactory
    @ContributesBinding(AppScope::class, binding<RootComponent.Factory>())
    interface Factory : RootComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            backHandler: BackHandler?,
            startupStatus: VoyagerStartupStatus,
        ): RootComponentImpl
    }
}
