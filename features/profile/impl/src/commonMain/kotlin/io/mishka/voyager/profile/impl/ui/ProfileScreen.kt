package io.mishka.voyager.profile.impl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.mishka.voyager.features.main.api.consts.MAIN_BOTTOM_BAR_HEIGHT
import io.mishka.voyager.profile.impl.ui.utils.sendEmail
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.button.VoyagerButton
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonSizes
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonStyles
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import io.mishkav.voyager.features.navigation.api.LocalRootNavigation
import io.mishkav.voyager.features.navigation.api.model.RootConfig
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import voyager.features.profile.impl.BuildKonfig
import voyager.features.profile.impl.generated.resources.Res
import voyager.features.profile.impl.generated.resources.avatar
import voyager.features.profile.impl.generated.resources.general_email
import voyager.features.profile.impl.generated.resources.profile_app_version
import voyager.features.profile.impl.generated.resources.profile_button_email_us
import voyager.features.profile.impl.generated.resources.profile_button_sign_out
import voyager.features.profile.impl.generated.resources.profile_tab_title
import voyager.features.profile.impl.generated.resources.profile_title

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    modifier: Modifier = Modifier,
) {
    val rootNavigation = LocalRootNavigation.current
    val signOutState = viewModel.signOutState.collectAsStateWithLifecycle()

    LaunchedEffect(signOutState.value) {
        if (signOutState.value is UIResult.Success) {
            rootNavigation.replaceAll(
                RootConfig.Auth(
                    successNavigationConfig = RootConfig.Main
                )
            )
        }
    }

    ProfileScreenContent(
        signOutState = signOutState,
        signOut = viewModel::signOut,
        modifier = modifier,
    )
}

@Composable
private fun ProfileScreenContent(
    signOutState: State<UIResult<Unit>>,
    signOut: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val email = stringResource(Res.string.general_email)

    Column(
        modifier = modifier
            .background(VoyagerTheme.colors.background)
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(bottom = MAIN_BOTTOM_BAR_HEIGHT + 12.dp, top = 16.dp)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.profile_tab_title),
            style = VoyagerTheme.typography.h1,
            textAlign = TextAlign.Start,
            color = VoyagerTheme.colors.font
        )

        Spacer(Modifier.height(38.dp))

        Image(
            modifier = Modifier
                .fillMaxWidth(0.52f)
                .aspectRatio(1f)
                .clip(CircleShape),
            painter = painterResource(Res.drawable.avatar),
            contentDescription = null,
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.profile_title),
            style = VoyagerTheme.typography.h2,
            textAlign = TextAlign.Center,
            color = VoyagerTheme.colors.font
        )

        Spacer(Modifier.height(24.dp))

        // TODO Stats

        Spacer(Modifier.weight(1f))

        VoyagerButton(
            modifier = Modifier.fillMaxWidth(),
            style = VoyagerDefaultButtonStyles.custom(
                containerColor = VoyagerTheme.colors.border,
                contentColor = VoyagerTheme.colors.font,
            ),
            size = VoyagerDefaultButtonSizes.buttonXL(),
            text = stringResource(Res.string.profile_button_email_us),
            onClick = { uriHandler.sendEmail(email) }
        )

        Spacer(Modifier.height(12.dp))

        VoyagerButton(
            modifier = Modifier.fillMaxWidth(),
            style = VoyagerDefaultButtonStyles.primary(),
            size = VoyagerDefaultButtonSizes.buttonXL(),
            text = stringResource(Res.string.profile_button_sign_out),
            loading = signOutState.value is UIResult.Success,
            onClick = signOut,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(Res.string.profile_app_version, BuildKonfig.VERSION_NAME),
            style = VoyagerTheme.typography.body,
            textAlign = TextAlign.Center,
            color = VoyagerTheme.colors.subtext
        )
    }
}
