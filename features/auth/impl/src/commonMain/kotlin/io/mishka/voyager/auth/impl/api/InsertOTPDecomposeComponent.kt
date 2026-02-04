package io.mishka.voyager.auth.impl.api

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.Provider
import io.mishka.voyager.auth.impl.ui.otp.OTPScreen
import io.mishka.voyager.auth.impl.ui.otp.OTPViewModel
import io.mishkav.voyager.core.ui.decompose.DecomposeOnBackParameter
import io.mishkav.voyager.core.ui.decompose.ScreenDecomposeComponent
import io.mishkav.voyager.core.ui.lifecycle.viewModelWithFactory
import io.mishkav.voyager.features.navigation.api.LocalRootNavigation
import io.mishkav.voyager.features.navigation.api.model.RootConfig

@AssistedInject
class InsertOTPDecomposeComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted private val email: String,
    @Assisted private val navigateBack: DecomposeOnBackParameter,
    @Assisted private val successNavigationConfig: RootConfig,
    private val otpViewModelProvider: Provider<OTPViewModel>,
) : ScreenDecomposeComponent(componentContext) {

    @Composable
    @Suppress("NonSkippableComposable")
    override fun Render() {
        val rootNavigation = LocalRootNavigation.current
        val viewModel = viewModelWithFactory {
            otpViewModelProvider()
        }

        OTPScreen(
            email = email,
            viewModel = viewModel,
            successNavigation = {
                rootNavigation.replaceCurrent(successNavigationConfig)
            },
            onBack = navigateBack::invoke,
            modifier = Modifier.fillMaxSize()
        )
    }

    @AssistedFactory
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            email: String,
            navigateBack: DecomposeOnBackParameter,
            successNavigationConfig: RootConfig,
        ): InsertOTPDecomposeComponent
    }
}
