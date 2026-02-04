package io.mishka.voyager.onboarding.impl.ui.vibes.blocks

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.appbar.SimpleVoyagerAppBar
import org.jetbrains.compose.resources.stringResource
import voyager.features.onboarding.impl.generated.resources.Res
import voyager.features.onboarding.impl.generated.resources.vibes_subtitle
import voyager.features.onboarding.impl.generated.resources.vibes_title

internal fun LazyListScope.vibeAppBarBlock(
    navigateBack: () -> Unit,
) {
    item(
        key = "vibe_app_bar",
        contentType = "vibe_app_bar",
    ) {
        VibeAppBarBlock(
            navigateBack = navigateBack,
        )
    }
}

@Composable
private fun VibeAppBarBlock(
    navigateBack: () -> Unit,
) {
    SimpleVoyagerAppBar(onBack = navigateBack)

    Spacer(Modifier.height(36.dp))

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(Res.string.vibes_title),
        style = VoyagerTheme.typography.h1,
        textAlign = TextAlign.Start,
        color = VoyagerTheme.colors.font
    )

    Spacer(Modifier.height(8.dp))

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(Res.string.vibes_subtitle),
        style = VoyagerTheme.typography.bodyBold,
        textAlign = TextAlign.Start,
        color = VoyagerTheme.colors.subtext
    )

    Spacer(Modifier.height(36.dp))
}
