package io.mishkav.voyager.core.ui.uikit.button

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import io.mishkav.voyager.core.ui.uikit.button.internal.VoyagerButtonLoadingAnimation
import io.mishkav.voyager.core.ui.uikit.button.internal.VoyagerButtonSize
import io.mishkav.voyager.core.ui.uikit.button.internal.VoyagerButtonStyle

@Composable
fun VoyagerButton(
    style: VoyagerButtonStyle,
    size: VoyagerButtonSize,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    val transition = updateTransition(loading, label = "Voyager button state")

    Button(
        modifier = modifier.heightIn(min = size.minHeight),
        shape = size.shape,
        border = style.border,
        colors = style.colors,
        contentPadding = size.paddingValues,
        elevation = style.elevation,
        enabled = enabled,
        onClick = onClick,
    ) {
        transition.AnimatedContent { loading ->
            if (loading) {
                VoyagerButtonLoadingAnimation(
                    circleColor = style.colors.contentColor
                )
            } else {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    icon?.let {
                        Icon(
                            modifier = Modifier.size(size.iconSize),
                            tint = style.iconColor,
                            imageVector = icon,
                            contentDescription = null,
                        )

                        Spacer(Modifier.width(size.iconSpacing))
                    }

                    Text(
                        style = size.textStyle,
                        textAlign = TextAlign.Center,
                        color = style.colors.contentColor,
                        overflow = TextOverflow.Ellipsis,
                        text = text,
                    )
                }
            }
        }
    }
}
