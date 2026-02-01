package io.mishkav.voyager.core.ui.uikit.appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.theme.icons.back16

@Composable
fun SimpleVoyagerAppBar(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(top = 16.dp),
    ) {
        Icon(
            imageVector = VoyagerTheme.icons.back16,
            contentDescription = null,
            tint = VoyagerTheme.colors.white,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterStart)
                .clickable(onClick = onBack),
        )
    }
}
