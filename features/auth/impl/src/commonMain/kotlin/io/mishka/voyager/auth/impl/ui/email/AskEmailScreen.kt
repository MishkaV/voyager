package io.mishka.voyager.auth.impl.ui.email

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.mishka.voyager.auth.impl.ui.email.state.AskEmailSnackbarState
import io.mishka.voyager.auth.impl.ui.email.state.AskEmailState
import io.mishkav.voyager.core.ui.decompose.DecomposeOnBackParameter
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.appbar.SimpleVoyagerAppBar
import io.mishkav.voyager.core.ui.uikit.button.VoyagerButton
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonSizes
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonStyles
import io.mishkav.voyager.core.ui.uikit.snackbar.VoyagerErrorSnackbar
import io.mishkav.voyager.core.ui.uikit.snackbar.compose.SnackbarBox
import io.mishkav.voyager.core.ui.uikit.snackbar.compose.noOverlapBottomContentBySnackbar
import io.mishkav.voyager.core.ui.uikit.textfield.VoyagerTextField
import org.jetbrains.compose.resources.stringResource
import voyager.features.auth.impl.generated.resources.Res
import voyager.features.auth.impl.generated.resources.email_error_not_right_email
import voyager.features.auth.impl.generated.resources.email_subtitle
import voyager.features.auth.impl.generated.resources.email_textfield_label
import voyager.features.auth.impl.generated.resources.email_textfield_placeholder
import voyager.features.auth.impl.generated.resources.email_title
import voyager.features.auth.impl.generated.resources.general_button_continue
import voyager.features.auth.impl.generated.resources.general_snackbar_error

@Composable
internal fun AskEmailScreen(
    navigateToOTP: (email: String) -> Unit,
    navigateBack: DecomposeOnBackParameter,
    viewModel: AskEmailViewModel,
    modifier: Modifier = Modifier,
) {
    val askEmailState = viewModel.askEmailState.collectAsStateWithLifecycle()

    LaunchedEffect(askEmailState.value) {
        val state = askEmailState.value
        if (state is AskEmailState.Success) {
            navigateToOTP(state.email)
        }
    }

    SnackbarBox(
        modifier = modifier,
        component = viewModel.snackbarComponent,
        alignment = Alignment.BottomCenter,
        snackbarContent = { state ->
            VoyagerErrorSnackbar(
                text = stringResource(
                    resource = when (state) {
                        AskEmailSnackbarState.WRONG_EMAIL -> Res.string.email_error_not_right_email
                        AskEmailSnackbarState.GENERAL_ERROR -> Res.string.general_snackbar_error
                    }
                ),
                close = viewModel.snackbarComponent::hide,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 12.dp),
            )
        },
    ) {
        AskEmailScreenContent(
            askEmailState = askEmailState,
            startEmailLogin = { email ->
                viewModel.startEmailLogin(
                    email = email,
                )
            },
            onBack = navigateBack::invoke,
            modifier = Modifier.fillMaxSize().imePadding(),
        )
    }
}

@Composable
private fun AskEmailScreenContent(
    askEmailState: State<AskEmailState>,
    startEmailLogin: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val emailState = rememberTextFieldState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
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
            text = stringResource(Res.string.email_title),
            style = VoyagerTheme.typography.h1,
            textAlign = TextAlign.Start,
            color = VoyagerTheme.colors.font
        )

        Spacer(Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.email_subtitle),
            style = VoyagerTheme.typography.bodyBold,
            textAlign = TextAlign.Start,
            color = VoyagerTheme.colors.subtext
        )

        Spacer(Modifier.height(36.dp))

        VoyagerTextField(
            modifier = Modifier
                .semantics {
                    contentType = ContentType.EmailAddress
                }
                .focusRequester(focusRequester),
            state = emailState,
            label = stringResource(Res.string.email_textfield_label),
            placeholder = stringResource(Res.string.email_textfield_placeholder),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        Spacer(Modifier.weight(1f))

        VoyagerButton(
            modifier = Modifier.fillMaxWidth().noOverlapBottomContentBySnackbar(),
            style = VoyagerDefaultButtonStyles.primary(),
            size = VoyagerDefaultButtonSizes.buttonXL(),
            text = stringResource(Res.string.general_button_continue),
            loading = askEmailState.value is AskEmailState.Loading,
            onClick = {
                startEmailLogin(emailState.text.toString())
            }
        )

        Spacer(Modifier.height(12.dp))
    }
}
