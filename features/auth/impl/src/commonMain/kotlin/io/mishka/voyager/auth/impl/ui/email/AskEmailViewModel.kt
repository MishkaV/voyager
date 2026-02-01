package io.mishka.voyager.auth.impl.ui.email

import co.touchlab.kermit.Logger
import dev.zacsweers.metro.Inject
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.mishka.voyager.auth.impl.ui.email.state.AskEmailSnackbarState
import io.mishka.voyager.auth.impl.ui.email.state.AskEmailState
import io.mishkav.voyager.core.ui.lifecycle.DecomposeViewModel
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarComponent
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarDuration
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Inject
class AskEmailViewModel(
    private val supabaseAuth: Auth,
) : DecomposeViewModel() {

    val snackbarComponent: SnackbarComponent<AskEmailSnackbarState> = SnackbarComponent()

    private val _askEmailState = MutableStateFlow(AskEmailState.IDLE)
    val askEmailState = _askEmailState.asStateFlow()

    fun startEmailLogin(email: String) {
        viewModelScope.launch {
            Logger.d("AskEmailViewModel: startEmailLogin")

            _askEmailState.value = AskEmailState.LOADING

            if (!isEmailValid(email)) {
                showSnackbar(AskEmailSnackbarState.WRONG_EMAIL)
                return@launch
            }

            runCatching {
                supabaseAuth.signInWith(OTP) {
                    this.email = email
                }
            }.onSuccess {
                _askEmailState.value = AskEmailState.SUCCESS
            }.onFailure { error ->
                Logger.e("AskEmailViewModel: Failed to send OTP - ${error.message}")
                showSnackbar(AskEmailSnackbarState.GENERAL_ERROR)
            }
        }
    }

    private fun showSnackbar(state: AskEmailSnackbarState) {
        _askEmailState.value = AskEmailState.IDLE
        snackbarComponent.show(
            SnackbarMessage(
                duration = SnackbarDuration.Short,
                content = state,
            )
        )
    }

    private fun isEmailValid(email: String): Boolean {
        if (email.isBlank()) return false

        return email.matches(emailAddressRegex)
    }

    override fun onDestroy() {
        super.onDestroy()
        snackbarComponent.onDestroy()
    }

    private companion object {
        private val emailAddressRegex = Regex(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
        )
    }
}
