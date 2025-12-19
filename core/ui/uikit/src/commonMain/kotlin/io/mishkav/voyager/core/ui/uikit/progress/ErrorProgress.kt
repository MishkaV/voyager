package io.mishkav.voyager.core.ui.uikit.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.theme.icons.error36

@Composable
fun ErrorProgress(
    modifier: Modifier = Modifier,
    background: Color = VoyagerTheme.colors.disabled,
    iconTint: Color = VoyagerTheme.colors.black,
) {
    Box(
        modifier = modifier.clipToBounds().background(background),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(36.dp),
            imageVector = VoyagerTheme.icons.error36,
            contentDescription = null,
            tint = iconTint,
        )
    }
}
