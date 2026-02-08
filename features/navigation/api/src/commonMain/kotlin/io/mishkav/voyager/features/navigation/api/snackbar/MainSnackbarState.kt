package io.mishkav.voyager.features.navigation.api.snackbar

sealed interface MainSnackbarState {

    val text: String
    val buttonText: String

    class Default(
        override val text: String,
        override val buttonText: String
    ) : MainSnackbarState
}
