package io.mishkav.voyager.core.ui.theme.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerIcon

val VoyagerIcon.google36: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Google36",
        defaultWidth = 36.dp,
        defaultHeight = 36.dp,
        viewportWidth = 36f,
        viewportHeight = 36f,
    ).apply {
        path(fill = SolidColor(Color(0xFF4285F4))) {
            moveTo(18.225f, 16f)
            verticalLineTo(20.26f)
            horizontalLineTo(24.265f)
            curveTo(24f, 21.63f, 23.204f, 22.79f, 22.01f, 23.57f)
            lineTo(25.653f, 26.34f)
            curveTo(27.776f, 24.42f, 29f, 21.6f, 29f, 18.25f)
            curveTo(29f, 17.47f, 28.929f, 16.72f, 28.796f, 16f)
            lineTo(18.225f, 16f)
            close()
        }
        path(fill = SolidColor(Color(0xFF34A853))) {
            moveTo(11.934f, 20.094f)
            lineTo(11.112f, 20.71f)
            lineTo(8.204f, 22.93f)
            curveTo(10.051f, 26.52f, 13.837f, 29f, 18.224f, 29f)
            curveTo(21.255f, 29f, 23.796f, 28.02f, 25.653f, 26.34f)
            lineTo(22.01f, 23.57f)
            curveTo(21.01f, 24.23f, 19.734f, 24.63f, 18.224f, 24.63f)
            curveTo(15.306f, 24.63f, 12.826f, 22.7f, 11.939f, 20.1f)
            lineTo(11.934f, 20.094f)
            close()
        }
        path(fill = SolidColor(Color(0xFFFBBC05))) {
            moveTo(8.204f, 13.07f)
            curveTo(7.439f, 14.55f, 7f, 16.22f, 7f, 18f)
            curveTo(7f, 19.78f, 7.439f, 21.45f, 8.204f, 22.93f)
            curveTo(8.204f, 22.94f, 11.939f, 20.09f, 11.939f, 20.09f)
            curveTo(11.714f, 19.43f, 11.582f, 18.73f, 11.582f, 18f)
            curveTo(11.582f, 17.27f, 11.714f, 16.57f, 11.939f, 15.91f)
            lineTo(8.204f, 13.07f)
            close()
        }
        path(fill = SolidColor(Color(0xFFEA4335))) {
            moveTo(18.225f, 11.38f)
            curveTo(19.878f, 11.38f, 21.347f, 11.94f, 22.52f, 13.02f)
            lineTo(25.735f, 9.87f)
            curveTo(23.786f, 8.09f, 21.255f, 7f, 18.225f, 7f)
            curveTo(13.837f, 7f, 10.051f, 9.47f, 8.204f, 13.07f)
            lineTo(11.939f, 15.91f)
            curveTo(12.826f, 13.31f, 15.306f, 11.38f, 18.225f, 11.38f)
            close()
        }
    }.build()
}
