package io.mishka.voyager.onboarding.impl.ui.userprefs.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.utils.clickableUnindicated

@Composable
fun PrefCheckbox(
    text: String,
    isSelected: Boolean,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = VoyagerTheme.colors
    val backgroundColor = animateColorAsState(
        targetValue = if (isSelected) {
            colors.brand.copy(alpha = 0.7f)
        } else {
            colors.border
        }
    )

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor.value)
            .clickableUnindicated { onClick(!isSelected) }
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = onClick,
            colors = CheckboxDefaults.colors(
                checkedColor = colors.brand,
                uncheckedColor = colors.font,
                checkmarkColor = colors.font,
            )
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = text,
            style = VoyagerTheme.typography.bodyBold,
            color = VoyagerTheme.colors.font
        )
    }
}
