package io.mishkav.voyager.core.ui.uikit.button

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.button.internal.VoyagerButtonStyle

object VoyagerDefaultButtonStyles {

    @Composable
    fun primary(): VoyagerButtonStyle {
        return VoyagerButtonStyle(
            colors = ButtonDefaults.buttonColors(
                containerColor = VoyagerTheme.colors.brand,
                contentColor = VoyagerTheme.colors.white,
                disabledContainerColor = VoyagerTheme.colors.disabled,
                disabledContentColor = VoyagerTheme.colors.white,
            ),
            iconColor = VoyagerTheme.colors.white,
            elevation = null,
            border = null
        )
    }

    @Composable
    fun secondary(): VoyagerButtonStyle {
        return VoyagerButtonStyle(
            colors = ButtonDefaults.buttonColors(
                containerColor = VoyagerTheme.colors.border,
                contentColor = VoyagerTheme.colors.black,
                disabledContainerColor = VoyagerTheme.colors.disabled,
                disabledContentColor = VoyagerTheme.colors.white,
            ),
            iconColor = VoyagerTheme.colors.black,
            elevation = null,
            border = null
        )
    }

    @Composable
    fun custom(
        containerColor: Color,
        contentColor: Color,
        iconColor: Color? = null,
        disabledContainerColor: Color = VoyagerTheme.colors.disabled,
        disabledContentColor: Color = VoyagerTheme.colors.white,
    ): VoyagerButtonStyle {
        return VoyagerButtonStyle(
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = disabledContainerColor,
                disabledContentColor = disabledContentColor,
            ),
            iconColor = iconColor ?: contentColor,
            elevation = null,
            border = null
        )
    }
}
