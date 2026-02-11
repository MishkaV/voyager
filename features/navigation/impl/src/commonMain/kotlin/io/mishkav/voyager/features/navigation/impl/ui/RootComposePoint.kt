package io.mishkav.voyager.features.navigation.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.LocalColors
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.snackbar.VoyagerSnackbar
import io.mishkav.voyager.core.ui.uikit.snackbar.compose.SnackbarBox
import io.mishkav.voyager.features.navigation.api.LocalRootNavigation
import io.mishkav.voyager.features.navigation.api.RootComponent
import io.mishkav.voyager.features.navigation.api.snackbar.LocalBottomMainSnackbarController
import io.mishkav.voyager.features.navigation.api.snackbar.LocalTopMainSnackbarController

@Composable
fun RootComposePoint(
    root: RootComponent,
    modifier: Modifier = Modifier,
) {
    VoyagerTheme {
        CompositionLocalProvider(
            LocalRootNavigation provides root,
            LocalBottomMainSnackbarController provides root.bottomSnackbarController,
            LocalTopMainSnackbarController provides root.topSnackbarController,
        ) {
            SnackbarBox(
                modifier = modifier.fillMaxSize(),
                component = root.topSnackbarController,
                alignment = Alignment.TopCenter,
                snackbarContent = { state ->
                    VoyagerSnackbar(
                        text = state.text,
                        buttonText = state.buttonText,
                        buttonClick = state.onButtonClick ?: root.topSnackbarController::hide,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .padding(top = 12.dp)
                            .windowInsetsPadding(WindowInsets.statusBars),
                    )
                },
            ) {
                SnackbarBox(
                    modifier = Modifier.fillMaxSize(),
                    component = root.bottomSnackbarController,
                    alignment = Alignment.BottomCenter,
                    snackbarContent = { state ->
                        VoyagerSnackbar(
                            text = state.text,
                            buttonText = state.buttonText,
                            buttonClick = state.onButtonClick ?: root.bottomSnackbarController::hide,
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .padding(bottom = 12.dp),
                        )
                    },
                ) {
                    root.Render(
                        modifier = modifier
                            .fillMaxSize()
                            .background(LocalColors.current.background),
                    )
                }
            }
        }
    }
}
