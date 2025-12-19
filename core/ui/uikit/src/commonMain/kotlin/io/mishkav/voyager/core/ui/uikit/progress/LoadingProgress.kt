package io.mishkav.voyager.core.ui.uikit.progress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme

@Composable
fun LoadingProgress(
    modifier: Modifier = Modifier,
    circleSize: Dp = 24.dp,
    circleWidth: Dp = 2.dp,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(circleSize)
                .align(Alignment.Center),
            color = VoyagerTheme.colors.brand,
            strokeWidth = circleWidth,
        )
    }
}
