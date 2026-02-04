package io.mishka.voyager.onboarding.impl.ui.vibes.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeEntity
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.utils.clickableUnindicated

@Composable
internal fun VibeBox(
    vibe: VibeEntity,
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

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor.value)
            .clickableUnindicated { onClick(!isSelected) }
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
    ) {
        Text(
            text = "${vibe.iconEmoji} ${vibe.title}",
            style = VoyagerTheme.typography.bodyBold,
            textAlign = TextAlign.Center,
            color = VoyagerTheme.colors.font
        )
    }
}
