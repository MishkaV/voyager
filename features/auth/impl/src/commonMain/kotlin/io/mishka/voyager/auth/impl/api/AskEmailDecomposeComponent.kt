package io.mishka.voyager.auth.impl.api

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.Provider
import io.mishka.voyager.auth.impl.ui.email.AskEmailScreen
import io.mishka.voyager.auth.impl.ui.login.LoginViewModel
import io.mishkav.voyager.core.ui.decompose.DecomposeOnBackParameter
import io.mishkav.voyager.core.ui.decompose.ScreenDecomposeComponent
import io.mishkav.voyager.core.ui.lifecycle.viewModelWithFactory

@AssistedInject
class AskEmailDecomposeComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted private val navigateBack: DecomposeOnBackParameter,
    private val askEmailViewModelProvider: Provider<LoginViewModel>,
) : ScreenDecomposeComponent(componentContext) {

    @Composable
    @Suppress("NonSkippableComposable")
    override fun Render() {
        val viewModel = viewModelWithFactory {
            askEmailViewModelProvider()
        }

        AskEmailScreen(
            navigateBack = navigateBack,
            viewModel = viewModel,
            modifier = Modifier.fillMaxSize(),
        )
    }

    @AssistedFactory
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            navigateBack: DecomposeOnBackParameter,
        ): AskEmailDecomposeComponent
    }
}
