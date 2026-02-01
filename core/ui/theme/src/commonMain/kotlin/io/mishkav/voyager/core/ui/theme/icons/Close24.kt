package io.mishkav.voyager.core.ui.theme.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerIcon

val VoyagerIcon.close24: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Close24",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(fill = SolidColor(Color(0xFFA3A3A3))) {
            moveTo(16.95f, 7.05f)
            curveTo(17.34f, 7.441f, 17.34f, 8.074f, 16.95f, 8.464f)
            lineTo(13.414f, 12f)
            lineTo(16.95f, 15.535f)
            curveTo(17.34f, 15.926f, 17.34f, 16.559f, 16.95f, 16.95f)
            curveTo(16.559f, 17.34f, 15.926f, 17.34f, 15.535f, 16.95f)
            lineTo(12f, 13.414f)
            lineTo(8.464f, 16.95f)
            curveTo(8.074f, 17.34f, 7.441f, 17.34f, 7.05f, 16.95f)
            curveTo(6.66f, 16.559f, 6.66f, 15.926f, 7.05f, 15.535f)
            lineTo(10.586f, 12f)
            lineTo(7.05f, 8.464f)
            curveTo(6.66f, 8.074f, 6.66f, 7.441f, 7.05f, 7.05f)
            curveTo(7.441f, 6.66f, 8.074f, 6.66f, 8.464f, 7.05f)
            lineTo(12f, 10.586f)
            lineTo(15.535f, 7.05f)
            curveTo(15.926f, 6.66f, 16.559f, 6.66f, 16.95f, 7.05f)
            close()
        }
    }.build()
}