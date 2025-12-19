package io.mishkav.voyager.core.ui.uikit.button.internal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Immutable
class VoyagerButtonSize(
    public val paddingValues: PaddingValues,
    public val iconSize: Dp,
    public val iconSpacing: Dp,
    public val shape: Shape,
    public val minHeight: Dp,
    public val textStyle: TextStyle,
)
