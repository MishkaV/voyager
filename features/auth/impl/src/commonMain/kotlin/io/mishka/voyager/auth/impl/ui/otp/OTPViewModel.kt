package io.mishka.voyager.auth.impl.ui.otp

import co.touchlab.kermit.Logger
import dev.zacsweers.metro.Inject
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.mishka.voyager.auth.impl.ui.otp.state.OTPSnackbarState
import io.mishka.voyager.auth.impl.ui.otp.state.OTPState
import io.mishkav.voyager.core.ui.lifecycle.DecomposeViewModel
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarComponent
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarDuration
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Inject
class OTPViewModel(
    private val supabaseAuth: Auth,
) : DecomposeViewModel() {

    val snackbarComponent: SnackbarComponent<OTPSnackbarState> = SnackbarComponent()

    private val _otpState = MutableStateFlow(OTPState.IDLE)
    val otpState = _otpState.asStateFlow()

    fun verifyOTP(
        email: String,
        otp: String,
    ) {
        viewModelScope.launch {
            Logger.d("OTPViewModel: verifyOTP")

            _otpState.value = OTPState.LOADING

            runCatching {
                supabaseAuth.verifyEmailOtp(
                    type = OtpType.Email.EMAIL,
                    email = email,
                    token = otp,
                )
            }.onSuccess {
                _otpState.value = OTPState.SUCCESS
            }.onFailure { error ->
                Logger.e("OTPViewModel: Failed to verify OTP - ${error.message}")
                showSnackbar(OTPSnackbarState.WRONG_OTP)
                _otpState.value = OTPState.IDLE
            }
        }
    }

    fun resendOTP(
        email: String,
    ) {
        viewModelScope.launch {
            Logger.d("OTPViewModel: resendOTP")

            runCatching {
                supabaseAuth.signInWith(OTP) {
                    this.email = email
                }
            }.onSuccess {
                showSnackbar(OTPSnackbarState.RESEND_CODE_COMPLETE)
            }.onFailure { error ->
                Logger.e("OTPViewModel: Failed to send OTP - ${error.message}")
                showSnackbar(OTPSnackbarState.RESEND_CODE_FAILED)
            }
        }
    }

    internal fun showSnackbar(state: OTPSnackbarState) {
        snackbarComponent.show(
            SnackbarMessage(
                duration = SnackbarDuration.Short,
                content = state,
            )
        )
    }
}
