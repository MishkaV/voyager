package io.mishka.voyager.features.main.impl.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.binding
import io.mishka.voyager.features.main.api.MainComponent
import io.mishka.voyager.features.main.impl.domain.model.MainBottomTab
import io.mishka.voyager.features.main.impl.domain.model.MainConfig
import io.mishka.voyager.features.main.impl.domain.model.toConfig
import io.mishka.voyager.features.main.impl.ui.MainScreen
import io.mishkav.voyager.core.ui.decompose.DecomposeComponent
import io.mishkav.voyager.core.ui.decompose.DecomposeOnBackParameter
import io.mishkav.voyager.core.ui.decompose.popOr

@AssistedInject
class MainComponentImpl(
    @Assisted componentContext: ComponentContext,
    @Assisted private val onBack: DecomposeOnBackParameter,
) : MainComponent<MainConfig>(), ComponentContext by componentContext, BackHandlerOwner {

    private val backCallback = BackCallback {
        navigation.popOr(onBack::invoke)
    }

    override val stack: Value<ChildStack<MainConfig, DecomposeComponent>> = childStack(
        source = navigation,
        serializer = MainConfig.serializer(),
        initialConfiguration = MainConfig.Home,
        handleBackButton = true,
        childFactory = ::child,
    )

    init {
        backHandler.register(backCallback)
    }

    private fun child(
        config: MainConfig,
        componentContext: ComponentContext,
    ): DecomposeComponent = when (config) {
        is MainConfig.Home -> TODO("Add screen implementation")

        is MainConfig.Search -> TODO("Add screen implementation")

        is MainConfig.Profile -> TODO("Add screen implementation")
    }

    @Composable
    override fun Render() {
        val childStack by stack.subscribeAsState()

        MainScreen(
            childStack = childStack,
            onTabClick = ::goToTab,
        )
    }

    private fun goToTab(tab: MainBottomTab) {
        navigation.bringToFront(tab.toConfig())
    }

    @AssistedFactory
    @ContributesBinding(AppScope::class, binding<MainComponent.Factory>())
    interface Factory : MainComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: DecomposeOnBackParameter,
        ): MainComponentImpl
    }
}
