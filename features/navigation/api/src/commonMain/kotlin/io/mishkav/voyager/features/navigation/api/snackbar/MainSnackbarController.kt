package io.mishkav.voyager.features.navigation.api.snackbar

import androidx.compose.runtime.compositionLocalOf
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarComponent

val LocalBottomMainSnackbarController = compositionLocalOf<SnackbarComponent<MainSnackbarState>> {
    error("Bottom main snackbar controller not provided")
}

val LocalTopMainSnackbarController = compositionLocalOf<SnackbarComponent<MainSnackbarState>> {
    error("Top main snackbar controller not provided")
}

class MainSnackbarController : SnackbarComponent<MainSnackbarState> by SnackbarComponent()
