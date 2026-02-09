package io.mishkav.voyager.features.navigation.api.snackbar

import androidx.compose.runtime.compositionLocalOf
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarComponent

val LocalBottomBottomMainSnackbarController = compositionLocalOf<SnackbarComponent<MainSnackbarState>> {
    error("Bottom main snackbar controller not provided")
}

class BottomMainSnackbarController : SnackbarComponent<MainSnackbarState> by SnackbarComponent()
