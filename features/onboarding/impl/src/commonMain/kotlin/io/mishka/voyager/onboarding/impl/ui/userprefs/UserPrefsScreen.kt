package io.mishka.voyager.onboarding.impl.ui.userprefs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.mishka.voyager.onboarding.impl.ui.userprefs.blocks.CheckboxBlock
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.appbar.SimpleVoyagerAppBar
import io.mishkav.voyager.core.ui.uikit.button.VoyagerButton
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonSizes
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonStyles
import org.jetbrains.compose.resources.stringResource
import voyager.features.onboarding.impl.generated.resources.Res
import voyager.features.onboarding.impl.generated.resources.general_button_continue
import voyager.features.onboarding.impl.generated.resources.prefs_subtitle
import voyager.features.onboarding.impl.generated.resources.prefs_title

@Composable
fun UserPrefsScreen(
    viewModel: UserPrefsViewModel,
    navigateToVibes: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val prefsState = viewModel.prefsState.collectAsStateWithLifecycle()

    UserPrefsScreenContent(
        prefsState = prefsState,
        selectedPrefsIds = viewModel.selectedPrefsIds,
        navigateToVibes = {
            viewModel.addUserPref(viewModel.selectedPrefsIds)
            navigateToVibes()
        },
        navigateBack = navigateBack,
        modifier = modifier,
    )
}

@Composable
private fun UserPrefsScreenContent(
    prefsState: State<PrefsUIState>,
    selectedPrefsIds: SnapshotStateList<String>,
    navigateToVibes: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(VoyagerTheme.colors.background)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        SimpleVoyagerAppBar(onBack = navigateBack)

        Spacer(Modifier.height(36.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.prefs_title),
            style = VoyagerTheme.typography.h1,
            textAlign = TextAlign.Start,
            color = VoyagerTheme.colors.font
        )

        Spacer(Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.prefs_subtitle),
            style = VoyagerTheme.typography.bodyBold,
            textAlign = TextAlign.Start,
            color = VoyagerTheme.colors.subtext
        )

        Spacer(Modifier.height(36.dp))

        CheckboxBlock(
            prefsState = prefsState,
            selectedPrefsIds = selectedPrefsIds,
        )

        Spacer(Modifier.weight(1f))

        VoyagerButton(
            modifier = Modifier.fillMaxWidth(),
            style = VoyagerDefaultButtonStyles.primary(),
            size = VoyagerDefaultButtonSizes.buttonXL(),
            text = stringResource(Res.string.general_button_continue),
            onClick = navigateToVibes
        )

        Spacer(Modifier.height(12.dp))
    }
}
