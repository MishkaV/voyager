package io.mishka.voyager.onboarding.impl.api

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.Provider
import io.mishka.voyager.onboarding.impl.ui.userprefs.UserPrefsScreen
import io.mishka.voyager.onboarding.impl.ui.userprefs.UserPrefsViewModel
import io.mishkav.voyager.core.ui.decompose.DecomposeOnBackParameter
import io.mishkav.voyager.core.ui.decompose.ScreenDecomposeComponent
import io.mishkav.voyager.core.ui.lifecycle.viewModelWithFactory

@AssistedInject
class UserPrefsDecomposeComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted private val navigateToVibes: () -> Unit,
    @Assisted private val navigateBack: DecomposeOnBackParameter,
    private val userPrefsViewModelProvider: Provider<UserPrefsViewModel>,
) : ScreenDecomposeComponent(componentContext) {

    @Composable
    @Suppress("NonSkippableComposable")
    override fun Render() {
        val viewModel = viewModelWithFactory<UserPrefsViewModel> {
            userPrefsViewModelProvider()
        }

        UserPrefsScreen(
            viewModel = viewModel,
            navigateToVibes = navigateToVibes,
            navigateBack = navigateBack::invoke,
            modifier = Modifier.fillMaxSize(),
        )
    }

    @AssistedFactory
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            navigateToVibes: () -> Unit,
            navigateBack: DecomposeOnBackParameter,
        ): UserPrefsDecomposeComponent
    }
}
