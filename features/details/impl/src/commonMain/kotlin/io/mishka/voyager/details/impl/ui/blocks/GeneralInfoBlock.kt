package io.mishka.voyager.details.impl.ui.blocks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme

internal fun LazyListScope.generalInfoBlock(
    modifier: Modifier = Modifier,
) {
    item(
        key = "GENERAL_INFO_BLOCK_KEY",
        contentType = "GENERAL_INFO_BLOCK",
    ) {
        GeneralInfoBlock(
            modifier = modifier,
        )
    }
}

@Composable
private fun GeneralInfoBlock(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        // TODO
    }
}

@Composable
private fun InfoBox(
    topic: String,
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(VoyagerTheme.colors.white.copy(alpha = 0.2f))
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = topic,
            style = VoyagerTheme.typography.caption,
            color = VoyagerTheme.colors.white.copy(alpha = 0.7f),
            textAlign = TextAlign.Start,
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = text,
            style = VoyagerTheme.typography.h2,
            color = VoyagerTheme.colors.white,
            textAlign = TextAlign.Start,
        )
    }
}
