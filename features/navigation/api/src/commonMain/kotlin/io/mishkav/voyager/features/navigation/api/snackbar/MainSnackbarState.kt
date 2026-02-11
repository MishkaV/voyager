package io.mishkav.voyager.features.navigation.api.snackbar

sealed interface MainSnackbarState {

    val text: String
    val buttonText: String

    val onButtonClick: (() -> Unit)?

    class Default(
        override val text: String,
        override val buttonText: String,
        override val onButtonClick: (() -> Unit)? = null,
    ) : MainSnackbarState
}
