package io.mishkav.voyager.core.ui.theme.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerIcon

val VoyagerIcon.back16: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Back16",
        defaultWidth = 16.dp,
        defaultHeight = 16.dp,
        viewportWidth = 16f,
        viewportHeight = 16f,
    ).apply {
        path(fill = SolidColor(Color(0xFF1D1B20))) {
            moveTo(3.825f, 9f)
            lineTo(9.425f, 14.6f)
            lineTo(8f, 16f)
            lineTo(0f, 8f)
            lineTo(8f, 0f)
            lineTo(9.425f, 1.4f)
            lineTo(3.825f, 7f)
            horizontalLineTo(16f)
            verticalLineTo(9f)
            horizontalLineTo(3.825f)
            close()
        }
    }.build()
}
