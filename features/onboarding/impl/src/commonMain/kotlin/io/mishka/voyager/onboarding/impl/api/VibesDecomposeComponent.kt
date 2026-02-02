package io.mishka.voyager.onboarding.impl.api

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.Provider
import io.mishka.voyager.onboarding.impl.ui.vibes.VibesScreen
import io.mishka.voyager.onboarding.impl.ui.vibes.VibesViewModel
import io.mishkav.voyager.core.ui.decompose.DecomposeOnBackParameter
import io.mishkav.voyager.core.ui.decompose.ScreenDecomposeComponent
import io.mishkav.voyager.core.ui.lifecycle.viewModelWithFactory
import io.mishkav.voyager.features.navigation.api.model.RootConfig

@AssistedInject
class VibesDecomposeComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted private val navigateBack: DecomposeOnBackParameter,
    @Assisted private val successNavigationConfig: RootConfig,
    private val vibesViewModelProvider: Provider<VibesViewModel>,
) : ScreenDecomposeComponent(componentContext) {

    @Composable
    @Suppress("NonSkippableComposable")
    override fun Render() {
        val viewModel = viewModelWithFactory<VibesViewModel> {
            vibesViewModelProvider()
        }

        VibesScreen(
            viewModel = viewModel,
            navigateBack = navigateBack::invoke,
            modifier = Modifier.fillMaxSize(),
        )
    }

    @AssistedFactory
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            navigateBack: DecomposeOnBackParameter,
            successNavigationConfig: RootConfig,
        ): VibesDecomposeComponent
    }
}
