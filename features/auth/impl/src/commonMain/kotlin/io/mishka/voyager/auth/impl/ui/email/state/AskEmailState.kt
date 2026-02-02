package io.mishka.voyager.auth.impl.ui.email.state

sealed interface AskEmailState {
    object Idle : AskEmailState

    object Loading : AskEmailState

    data class Success(
        val email: String,
    ) : AskEmailState
}
