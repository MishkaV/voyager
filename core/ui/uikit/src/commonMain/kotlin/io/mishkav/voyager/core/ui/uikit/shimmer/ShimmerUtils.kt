package io.mishkav.voyager.core.ui.uikit.shimmer

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eygraber.compose.placeholder.PlaceholderHighlight
import com.eygraber.compose.placeholder.fade
import com.eygraber.compose.placeholder.placeholder
import io.mishkav.voyager.core.ui.theme.VoyagerTheme

@Composable
fun Modifier.placeholderFadeConnecting(
    shape: CornerBasedShape = RoundedCornerShape(4.dp),
    visible: Boolean = true,
): Modifier {
    val colors = VoyagerTheme.colors
    val placeholderModifier = Modifier.Companion.placeholder(
        visible = visible,
        shape = shape,
        color = colors.disabled.copy(alpha = 0.2f),
        highlight = PlaceholderHighlight.fade(
            highlightColor = colors.disabled,
        ),
    )

    return this then placeholderModifier
}
