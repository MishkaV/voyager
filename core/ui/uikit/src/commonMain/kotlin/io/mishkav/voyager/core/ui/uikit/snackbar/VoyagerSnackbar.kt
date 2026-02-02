package io.mishkav.voyager.core.ui.uikit.snackbar

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme

@Composable
fun VoyagerSnackbar(
    text: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    buttonText: String? = null,
    buttonClick: (() -> Unit)? = null,
) {
    val trailingIcon: (@Composable () -> Unit)? = if (buttonText != null && buttonClick != null) {
        {
            BaseVoyagerSnackbarButton(
                text = buttonText,
                contentColor = VoyagerTheme.colors.brand,
                backgroundColor = VoyagerTheme.colors.brand.copy(alpha = 0.1f),
                onClick = buttonClick
            )
        }
    } else {
        null
    }

    BaseVoyagerSnackbar(
        text = text,
        modifier = modifier,
        contentColor = VoyagerTheme.colors.black,
        backgroundColor = VoyagerTheme.colors.white,
        icon = icon?.let {
            {
                Icon(
                    modifier = Modifier.size(36.dp),
                    imageVector = icon,
                    contentDescription = null,
                    tint = VoyagerTheme.colors.black,
                )
            }
        },
        trailingIcon = trailingIcon,
    )
}
