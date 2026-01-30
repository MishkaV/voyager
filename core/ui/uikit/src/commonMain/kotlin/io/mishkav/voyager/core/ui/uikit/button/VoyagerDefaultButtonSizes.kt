package io.mishkav.voyager.core.ui.uikit.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.button.internal.VoyagerButtonSize

object VoyagerDefaultButtonSizes {

    @Composable
    fun buttonXL(
        verticalPadding: Dp = 16.dp,
        horizontalPadding: Dp = 20.dp,
        iconSize: Dp = 16.dp,
        iconSpacing: Dp = 8.dp,
    ): VoyagerButtonSize {
        return VoyagerButtonSize(
            paddingValues = PaddingValues(
                vertical = verticalPadding,
                horizontal = horizontalPadding
            ),
            iconSize = iconSize,
            iconSpacing = iconSpacing,
            shape = RoundedCornerShape(16.dp),
            minHeight = 52.dp,
            textStyle = VoyagerTheme.typography.buttonXL,
        )
    }

    @Composable
    fun buttonL(
        verticalPadding: Dp = 9.dp,
        horizontalPadding: Dp = 16.dp,
        iconSize: Dp = 16.dp,
        iconSpacing: Dp = 8.dp,
    ): VoyagerButtonSize {
        return VoyagerButtonSize(
            paddingValues = PaddingValues(
                vertical = verticalPadding,
                horizontal = horizontalPadding
            ),
            iconSize = iconSize,
            iconSpacing = iconSpacing,
            shape = RoundedCornerShape(12.dp),
            minHeight = 44.dp,
            textStyle = VoyagerTheme.typography.buttonL,
        )
    }
}
