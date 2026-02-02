package io.mishka.voyager.auth.impl.ui.otp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.mishka.voyager.auth.impl.ui.otp.components.MAX_OTP_LENGTH
import io.mishka.voyager.auth.impl.ui.otp.components.OTPTextField
import io.mishka.voyager.auth.impl.ui.otp.components.ResendTimer
import io.mishka.voyager.auth.impl.ui.otp.state.OTPSnackbarState
import io.mishka.voyager.auth.impl.ui.otp.state.OTPState
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.appbar.SimpleVoyagerAppBar
import io.mishkav.voyager.core.ui.uikit.button.VoyagerButton
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonSizes
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonStyles
import io.mishkav.voyager.core.ui.uikit.snackbar.VoyagerErrorSnackbar
import io.mishkav.voyager.core.ui.uikit.snackbar.VoyagerSnackbar
import io.mishkav.voyager.core.ui.uikit.snackbar.compose.SnackbarBox
import io.mishkav.voyager.core.ui.uikit.snackbar.compose.noOverlapBottomContentBySnackbar
import org.jetbrains.compose.resources.stringResource
import voyager.features.auth.impl.generated.resources.Res
import voyager.features.auth.impl.generated.resources.general_button_continue
import voyager.features.auth.impl.generated.resources.general_snackbar_error
import voyager.features.auth.impl.generated.resources.otp_error_code
import voyager.features.auth.impl.generated.resources.otp_snackbar_resend_button_confirm
import voyager.features.auth.impl.generated.resources.otp_snackbar_resend_complete
import voyager.features.auth.impl.generated.resources.otp_subtitle
import voyager.features.auth.impl.generated.resources.otp_title

@Composable
internal fun OTPScreen(
    email: String,
    viewModel: OTPViewModel,
    successNavigation: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val otpState = viewModel.otpState.collectAsStateWithLifecycle()

    LaunchedEffect(otpState.value) {
        if (otpState.value == OTPState.SUCCESS) {
            successNavigation()
        }
    }

    SnackbarBox(
        modifier = modifier,
        component = viewModel.snackbarComponent,
        alignment = Alignment.BottomCenter,
        snackbarContent = { state ->
            val generalModifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 12.dp)

            when (state) {
                OTPSnackbarState.WRONG_OTP, OTPSnackbarState.RESEND_CODE_FAILED -> {
                    VoyagerErrorSnackbar(
                        text = stringResource(
                            resource = when (state) {
                                OTPSnackbarState.WRONG_OTP -> Res.string.otp_error_code
                                OTPSnackbarState.RESEND_CODE_FAILED -> Res.string.general_snackbar_error
                                OTPSnackbarState.RESEND_CODE_COMPLETE -> Res.string.general_snackbar_error
                            }
                        ),
                        close = viewModel.snackbarComponent::hide,
                        modifier = generalModifier,
                    )
                }

                OTPSnackbarState.RESEND_CODE_COMPLETE -> {
                    VoyagerSnackbar(
                        text = stringResource(Res.string.otp_snackbar_resend_complete),
                        buttonText = stringResource(Res.string.otp_snackbar_resend_button_confirm),
                        buttonClick = viewModel.snackbarComponent::hide,
                        modifier = generalModifier
                    )
                }
            }
        },
    ) {
        OTPScreenContent(
            email = email,
            otpState = otpState,
            resendCode = { viewModel.resendOTP(email) },
            verifyOTP = { otp -> viewModel.verifyOTP(email, otp) },
            onBack = onBack,
            modifier = Modifier.fillMaxSize().imePadding(),
        )
    }
}

@Composable
private fun OTPScreenContent(
    email: String,
    otpState: State<OTPState>,
    resendCode: () -> Unit,
    verifyOTP: (otp: String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val otpCodeState = rememberTextFieldState()
    val isPrimaryButtonActive = remember(otpCodeState.text) {
        derivedStateOf {
            otpCodeState.text.length == MAX_OTP_LENGTH
        }
    }

    Column(
        modifier = modifier
            .background(VoyagerTheme.colors.background)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        SimpleVoyagerAppBar(onBack = onBack)

        Spacer(Modifier.height(36.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.otp_title),
            style = VoyagerTheme.typography.h1,
            textAlign = TextAlign.Start,
            color = VoyagerTheme.colors.font
        )

        Spacer(Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.otp_subtitle, email),
            style = VoyagerTheme.typography.bodyBold,
            textAlign = TextAlign.Start,
            color = VoyagerTheme.colors.subtext
        )

        Spacer(Modifier.height(36.dp))

        OTPTextField(
            state = otpCodeState,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        ResendTimer(resendCode = resendCode)

        Spacer(Modifier.weight(1f))

        VoyagerButton(
            modifier = Modifier.fillMaxWidth().noOverlapBottomContentBySnackbar(),
            style = VoyagerDefaultButtonStyles.primary(),
            size = VoyagerDefaultButtonSizes.buttonXL(),
            text = stringResource(Res.string.general_button_continue),
            loading = otpState.value == OTPState.LOADING,
            enabled = isPrimaryButtonActive.value,
            onClick = {
                verifyOTP(otpCodeState.text.toString())
            }
        )

        Spacer(Modifier.height(12.dp))
    }
}
