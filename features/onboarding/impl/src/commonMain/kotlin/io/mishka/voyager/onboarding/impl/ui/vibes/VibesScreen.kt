package io.mishka.voyager.onboarding.impl.ui.vibes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateSet
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.mishka.voyager.onboarding.impl.ui.vibes.blocks.vibeAppBarBlock
import io.mishka.voyager.onboarding.impl.ui.vibes.blocks.vibesListBlock
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.button.VoyagerButton
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonSizes
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonStyles
import io.mishkav.voyager.features.navigation.api.LocalRootNavigation
import io.mishkav.voyager.features.navigation.api.model.RootConfig
import org.jetbrains.compose.resources.stringResource
import voyager.features.onboarding.impl.generated.resources.Res
import voyager.features.onboarding.impl.generated.resources.general_button_continue

@Composable
fun VibesScreen(
    viewModel: VibesViewModel,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val rootNavigation = LocalRootNavigation.current
    val vibesState = viewModel.vibesState.collectAsStateWithLifecycle()

    VibesScreenContent(
        vibesState = vibesState,
        selectedVibeIds = viewModel.selectedVibeIds,
        navigateToLocation = {
            viewModel.addSelectedVibes()
            viewModel.setOnboardingAsCompleted()
            rootNavigation.push(
                config = RootConfig.Location {
                    rootNavigation.replaceAll(RootConfig.Main)
                }
            )
        },
        navigateBack = navigateBack,
        modifier = modifier,
    )
}

@Composable
private fun VibesScreenContent(
    vibesState: State<VibesUIState>,
    selectedVibeIds: SnapshotStateSet<String>,
    navigateToLocation: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(VoyagerTheme.colors.background)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 24.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            vibeAppBarBlock(navigateBack = navigateBack)

            vibesListBlock(
                selectedVibeIds = selectedVibeIds,
                vibesState = vibesState,
            )
        }

        VoyagerButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            style = VoyagerDefaultButtonStyles.primary(),
            size = VoyagerDefaultButtonSizes.buttonXL(),
            text = stringResource(Res.string.general_button_continue),
            onClick = navigateToLocation
        )
    }
}
