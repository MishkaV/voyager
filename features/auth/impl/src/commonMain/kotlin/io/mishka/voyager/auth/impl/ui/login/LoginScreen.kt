package io.mishka.voyager.auth.impl.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.mishka.voyager.auth.impl.ui.login.components.TypewriterText
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.theme.icons.google36
import io.mishkav.voyager.core.ui.theme.icons.mail36
import io.mishkav.voyager.core.ui.uikit.button.VoyagerButton
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonSizes
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonStyles
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import voyager.features.auth.impl.generated.resources.Res
import voyager.features.auth.impl.generated.resources.ic_logo
import voyager.features.auth.impl.generated.resources.login_button_email
import voyager.features.auth.impl.generated.resources.login_button_google

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier,
) {
    LoginScreenContent(
        modifier = modifier,
    )
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
) {
    val topSpaceWeight = 0.67f
    val textWidthWeight = 0.67f

    Column(
        modifier = modifier
            .background(VoyagerTheme.colors.background)
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(topSpaceWeight))

        Image(
            painter = painterResource(Res.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.size(128.dp).clip(RoundedCornerShape(36.dp)),
        )

        Spacer(Modifier.height(14.dp))

        TypewriterText(
            modifier = Modifier
                .fillMaxWidth(textWidthWeight)
                .heightIn(72.dp),
        )

        Spacer(Modifier.height(70.dp))

        VoyagerButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            style = VoyagerDefaultButtonStyles.custom(
                containerColor = VoyagerTheme.colors.white,
                contentColor = VoyagerTheme.colors.black,
                iconColor = Color.Unspecified,
            ),
            size = VoyagerDefaultButtonSizes.buttonXL(
                verticalPadding = 9.dp,
                iconSize = 36.dp,
            ),
            text = stringResource(Res.string.login_button_google),
            icon = VoyagerTheme.icons.google36,
            onClick = {
                // TODO Google logic
            }
        )

        Spacer(Modifier.height(10.dp))

        VoyagerButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            style = VoyagerDefaultButtonStyles.custom(
                containerColor = VoyagerTheme.colors.white,
                contentColor = VoyagerTheme.colors.black,
                iconColor = Color.Unspecified,
            ),
            size = VoyagerDefaultButtonSizes.buttonXL(
                verticalPadding = 9.dp,
                iconSize = 36.dp,
            ),
            text = stringResource(Res.string.login_button_email),
            icon = VoyagerTheme.icons.mail36,
            onClick = {
                // TODO Email navigation
            }
        )

        Spacer(Modifier.weight(1 - topSpaceWeight))
    }
}
