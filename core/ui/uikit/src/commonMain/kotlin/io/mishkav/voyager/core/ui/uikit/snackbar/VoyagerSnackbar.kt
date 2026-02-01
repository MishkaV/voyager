package io.mishkav.voyager.core.ui.uikit.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.theme.icons.close24

@Composable
fun VoyagerSnackbar(
    text: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    close: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(VoyagerTheme.colors.brand)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon?.let {
            Icon(
                modifier = Modifier.size(36.dp),
                imageVector = icon,
                contentDescription = null,
                tint = VoyagerTheme.colors.font,
            )

            Spacer(Modifier.width(12.dp))
        }

        Text(
            text = text,
            style = VoyagerTheme.typography.buttonL,
            color = VoyagerTheme.colors.font
        )

        close?.let {
            Spacer(Modifier.width(12.dp))

            Icon(
                modifier = Modifier.clickable { close() },
                imageVector = VoyagerTheme.icons.close24,
                contentDescription = null,
                tint = VoyagerTheme.colors.font,
            )
        }
    }
}
