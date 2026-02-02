package io.mishka.voyager.intro.impl.api

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
import io.mishka.voyager.intro.api.IntroComponent
import io.mishka.voyager.intro.impl.ui.IntroScreen
import io.mishka.voyager.intro.impl.ui.IntroViewModel
import io.mishkav.voyager.core.ui.lifecycle.viewModelWithFactory

@AssistedInject
class IntroComponentImpl(
    @Assisted componentContext: ComponentContext,
    private val introViewModelProvider: Provider<IntroViewModel>,
) : IntroComponent(componentContext), ComponentContext by componentContext, BackHandlerOwner {

    @Composable
    override fun Render() {
        val viewModel = viewModelWithFactory { introViewModelProvider() }

        IntroScreen(
            viewModel = viewModel,
        )
    }

    @AssistedFactory
    @ContributesBinding(AppScope::class, binding<IntroComponent.Factory>())
    interface Factory : IntroComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
        ): IntroComponentImpl
    }
}
