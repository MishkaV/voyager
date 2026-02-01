package io.mishka.voyager.auth.impl.ui.email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mishka.voyager.auth.impl.ui.login.LoginViewModel
import io.mishkav.voyager.core.ui.decompose.DecomposeOnBackParameter
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.appbar.SimpleVoyagerAppBar
import io.mishkav.voyager.core.ui.uikit.button.VoyagerButton
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonSizes
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonStyles
import io.mishkav.voyager.core.ui.uikit.textfield.VoyagerTextField
import org.jetbrains.compose.resources.stringResource
import voyager.features.auth.impl.generated.resources.Res
import voyager.features.auth.impl.generated.resources.email_button
import voyager.features.auth.impl.generated.resources.email_subtitle
import voyager.features.auth.impl.generated.resources.email_textfield_label
import voyager.features.auth.impl.generated.resources.email_textfield_placeholder
import voyager.features.auth.impl.generated.resources.email_title

@Composable
internal fun AskEmailScreen(
    navigateBack: DecomposeOnBackParameter,
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier,
) {
    AskEmailScreenContent(
        onBack = navigateBack::invoke,
        modifier = modifier,
    )
}

@Composable
private fun AskEmailScreenContent(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val emailState = rememberTextFieldState()

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
            modifier = Modifier.semantics {
                contentType = ContentType.EmailAddress
            },
            state = emailState,
            label = stringResource(Res.string.email_textfield_label),
            placeholder = stringResource(Res.string.email_textfield_placeholder),
        )

        Spacer(Modifier.weight(1f))

        VoyagerButton(
            modifier = Modifier.fillMaxWidth(),
            style = VoyagerDefaultButtonStyles.primary(),
            size = VoyagerDefaultButtonSizes.buttonXL(),
            text = stringResource(Res.string.email_button),
            onClick = {
                // TODO
            }
        )

        Spacer(Modifier.height(12.dp))
    }
}
