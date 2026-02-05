package io.mishka.voyager.home.impl.api

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.binding
import io.mishka.voyager.home.api.HomeComponent
import io.mishka.voyager.home.impl.ui.HomeScreen
import io.mishka.voyager.home.impl.ui.HomeViewModel
import io.mishkav.voyager.core.ui.lifecycle.viewModelWithFactory

@AssistedInject
class HomeComponentImpl(
    @Assisted componentContext: ComponentContext,
    private val homeViewModelProvider: Provider<HomeViewModel>,
) : HomeComponent(componentContext), ComponentContext by componentContext, BackHandlerOwner {

    @Composable
    override fun Render() {
        val viewModel = viewModelWithFactory {
            homeViewModelProvider()
        }

        HomeScreen(
            viewModel = viewModel,
        )
    }

    @AssistedFactory
    @ContributesBinding(AppScope::class, binding<HomeComponent.Factory>())
    interface Factory : HomeComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
        ): HomeComponentImpl
    }
}
