package io.mishkav.voyager.core.ui.uikit.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.button.internal.VoyagerButtonSize

object VoyagerDefaultButtonSizes {

    @Composable
    fun buttonXL(): VoyagerButtonSize {
        return VoyagerButtonSize(
            paddingValues = PaddingValues(vertical = 16.dp, horizontal = 20.dp),
            iconSize = 16.dp,
            iconSpacing = 8.dp,
            shape = RoundedCornerShape(16.dp),
            minHeight = 52.dp,
            textStyle = VoyagerTheme.typography.buttonXL,
        )
    }

    @Composable
    fun buttonL(): VoyagerButtonSize {
        return VoyagerButtonSize(
            paddingValues = PaddingValues(vertical = 9.dp, horizontal = 16.dp),
            iconSize = 16.dp,
            iconSpacing = 8.dp,
            shape = RoundedCornerShape(12.dp),
            minHeight = 44.dp,
            textStyle = VoyagerTheme.typography.buttonL,
        )
    }
}
