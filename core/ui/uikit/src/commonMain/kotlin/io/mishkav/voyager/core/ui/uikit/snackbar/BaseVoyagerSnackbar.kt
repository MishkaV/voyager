package io.mishkav.voyager.core.ui.uikit.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme

@Composable
internal fun BaseVoyagerSnackbar(
    text: String,
    modifier: Modifier = Modifier,
    contentColor: Color = VoyagerTheme.colors.font,
    backgroundColor: Color = VoyagerTheme.colors.brand,
    icon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon?.let {
            icon()

            Spacer(Modifier.width(12.dp))
        }

        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = VoyagerTheme.typography.buttonL,
            color = contentColor,
        )

        trailingIcon?.let {
            Spacer(Modifier.width(12.dp))

            trailingIcon()
        }
    }
}

@Composable
internal fun BaseVoyagerSnackbarButton(
    text: String,
    contentColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = VoyagerTheme.typography.buttonL,
            color = contentColor,
        )
    }
}
