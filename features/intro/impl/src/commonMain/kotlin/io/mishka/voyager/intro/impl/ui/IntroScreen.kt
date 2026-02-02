package io.mishka.voyager.intro.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.mishka.voyager.intro.impl.api.IntroState
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.button.VoyagerButton
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonSizes
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonStyles
import io.mishkav.voyager.features.navigation.api.LocalRootNavigation
import io.mishkav.voyager.features.navigation.api.model.RootConfig
import org.jetbrains.compose.resources.stringResource
import voyager.features.intro.impl.generated.resources.Res
import voyager.features.intro.impl.generated.resources.button_next
import voyager.features.intro.impl.generated.resources.title

@Composable
fun IntroScreen(
    viewModel: IntroViewModel,
    modifier: Modifier = Modifier,
) {
    val rootNavigation = LocalRootNavigation.current
    val introState = viewModel.introState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(VoyagerTheme.colors.background),
    ) {
        VoyagerBackground(modifier = Modifier.fillMaxSize())

        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = stringResource(Res.string.title),
            style = VoyagerTheme.typography.h1.copy(
                fontSize = 64.sp,
                textAlign = TextAlign.Center,
                color = VoyagerTheme.colors.font,
            )
        )

        VoyagerButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(bottom = 12.dp)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            style = VoyagerDefaultButtonStyles.primary(),
            size = VoyagerDefaultButtonSizes.buttonXL(),
            text = stringResource(Res.string.button_next),
            onClick = {
                val configToNavigate = when (introState.value) {
                    IntroState.ShouldShowAuth -> RootConfig.Auth(
                        nextScreenToNavigate = RootConfig.Onboarding,
                    )
                    IntroState.ShouldShowOnboarding -> RootConfig.Onboarding
                    null -> null
                }

                configToNavigate?.let { rootNavigation.push(configToNavigate) }
            }
        )
    }
}

@Composable
expect fun VoyagerBackground(modifier: Modifier = Modifier)
