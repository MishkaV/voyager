package io.mishkav.voyager.core.ui.theme.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerIcon

val VoyagerIcon.close16: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Close16",
        defaultWidth = 16.dp,
        defaultHeight = 16.dp,
        viewportWidth = 16f,
        viewportHeight = 16f,
    ).apply {
        path(fill = SolidColor(Color(0xFF1D1B20))) {
            moveTo(8f, 6.586f)
            lineTo(13.657f, 0.929f)
            lineTo(15.071f, 2.343f)
            lineTo(9.414f, 8f)
            lineTo(15.071f, 13.657f)
            lineTo(13.657f, 15.071f)
            lineTo(8f, 9.414f)
            lineTo(2.343f, 15.071f)
            lineTo(0.929f, 13.657f)
            lineTo(6.586f, 8f)
            lineTo(0.929f, 2.343f)
            lineTo(2.343f, 0.929f)
            lineTo(8f, 6.586f)
            close()
        }
    }.build()
}
