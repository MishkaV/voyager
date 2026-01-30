package io.mishkav.voyager.core.ui.uikit.button.internal

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
class VoyagerButtonStyle(
    val colors: ButtonColors,
    val iconColor: Color,
    val elevation: ButtonElevation?,
    val border: BorderStroke?,
)
