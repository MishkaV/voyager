package io.mishkav.voyager.features.navigation.impl.api

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.androidPredictiveBackAnimatableV2
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
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
import io.mishka.voyager.details.api.CountryAiSuggestComponent
import io.mishka.voyager.details.api.CountryDetailsComponent
import io.mishka.voyager.details.api.models.CountryAiSuggestArgs
import io.mishka.voyager.details.api.models.CountryDetailsArgs
import io.mishka.voyager.features.main.api.MainComponent
import io.mishka.voyager.intro.api.IntroComponent
import io.mishka.voyager.location.api.LocationComponent
import io.mishka.voyager.onboarding.api.OnboardingComponent
import io.mishkav.voyager.core.ui.decompose.DecomposeComponent
import io.mishkav.voyager.core.ui.decompose.bottomsheet.SlotModalBottomSheet
import io.mishkav.voyager.core.ui.uikit.transition.LocalNavAnimatedVisibilityScope
import io.mishkav.voyager.core.ui.uikit.transition.LocalSharedTransitionScope
import io.mishkav.voyager.features.navigation.api.RootComponent
import io.mishkav.voyager.features.navigation.api.bottomsheet.SheetConfig
import io.mishkav.voyager.features.navigation.api.model.RootConfig
import io.mishkav.voyager.features.navigation.api.model.VoyagerStartupStatus
import io.mishkav.voyager.features.navigation.api.snackbar.BottomMainSnackbarController

@Suppress("LongParameterList")
@AssistedInject
class RootComponentImpl(
    @Assisted componentContext: ComponentContext,
    @Assisted externalBackHandler: BackHandler?,
    @Assisted startupStatus: VoyagerStartupStatus,
    private val authComponentFactory: AuthComponent.Factory,
    private val countryAiSuggestComponentFactory: CountryAiSuggestComponent.Factory,
    private val countryDetailsComponentFactory: CountryDetailsComponent.Factory,
    private val introComponentFactory: IntroComponent.Factory,
    private val locationComponentFactory: LocationComponent.Factory,
    private val mainComponentFactory: MainComponent.Factory,
    private val onboardingComponentFactory: OnboardingComponent.Factory,
) : RootComponent, ComponentContext by componentContext, BackHandlerOwner {

    override val backHandler: BackHandler = externalBackHandler ?: BackDispatcher()

    override val bottomSnackbarController by lazy { BottomMainSnackbarController() }

    private val navigation = StackNavigation<RootConfig>()

    private val sheetNavigation = SlotNavigation<SheetConfig>()
    private val sheetSlot: Value<ChildSlot<SheetConfig, DecomposeComponent>> = childSlot(
        source = sheetNavigation,
        handleBackButton = true,
        serializer = SheetConfig.serializer(),
        childFactory = ::sheetChild
    )

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
        is RootConfig.Main -> mainComponentFactory.create(
            componentContext = componentContext,
            onBack = ::goBack,
        )

        is RootConfig.Auth -> authComponentFactory.create(
            componentContext = componentContext,
            successNavigationConfig = config.successNavigationConfig,
        )

        is RootConfig.CountryDetails -> countryDetailsComponentFactory.create(
            componentContext = componentContext,
            navigateBack = ::goBack,
            args = CountryDetailsArgs(
                countryId = config.countryId,
                name = config.name,
                flagFullPatch = config.flagFullPatch,
                backgroundHex = config.backgroundHex,
            )
        )

        is RootConfig.Onboarding -> onboardingComponentFactory.create(
            componentContext = componentContext,
        )

        is RootConfig.Intro -> introComponentFactory.create(
            componentContext = componentContext,
        )

        is RootConfig.Location -> locationComponentFactory.create(
            componentContext = componentContext,
            onRequestLocation = { isGranted ->
                navigation.replaceAll(config.successNavigationConfig)
            },
            onBack = ::goBack
        )
    }

    private fun sheetChild(
        config: SheetConfig,
        childComponentContext: ComponentContext
    ): DecomposeComponent = when (config) {
        is SheetConfig.RequestVoyagerAI -> countryAiSuggestComponentFactory.create(
            componentContext = childComponentContext,
            args = CountryAiSuggestArgs(
                aiSuggestId = config.aiSuggestId,
                backgroundHex = config.backgroundHex,
                countryId = config.countryId,
            ),
        )
    }

    @OptIn(ExperimentalDecomposeApi::class, ExperimentalSharedTransitionApi::class)
    @Composable
    override fun Render(modifier: Modifier) {
        val childStack by stack.subscribeAsState()
        val animationSpec = tween<Float>(durationMillis = 500)

        SharedTransitionLayout(
            modifier = modifier,
        ) {
            CompositionLocalProvider(
                LocalSharedTransitionScope provides this@SharedTransitionLayout
            ) {
                ChildStack(
                    modifier = Modifier.fillMaxSize(),
                    stack = childStack,
                    animation = stackAnimation(
                        animator = fade(animationSpec) + scale(animationSpec),
                        predictiveBackParams = { childStack ->
                            val activeConfig = childStack.active.configuration

                            if (activeConfig is RootConfig.CountryDetails) {
                                null
                            } else {
                                PredictiveBackParams(
                                    backHandler = backHandler,
                                    onBack = ::goBack,
                                    animatable = ::androidPredictiveBackAnimatableV2,
                                )
                            }
                        },
                    ),
                ) {
                    CompositionLocalProvider(
                        LocalNavAnimatedVisibilityScope provides this
                    ) {
                        it.instance.Render()

                        SlotModalBottomSheet(
                            childSlotValue = sheetSlot,
                            onDismiss = sheetNavigation::dismiss,
                            content = { child -> child.Render() }
                        )
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

    override fun replaceAll(vararg configs: RootConfig) {
        navigation.replaceAll(*configs)
    }

    override fun openBottomSheet(config: SheetConfig) {
        sheetNavigation.activate(config)
    }

    override fun closeBottomSheet() {
        sheetNavigation.dismiss()
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
