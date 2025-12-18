package io.mishkav.voyager.core.ui.theme.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerIcon

val VoyagerIcon.mail36: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Mail36",
        defaultWidth = 36.dp,
        defaultHeight = 36.dp,
        viewportWidth = 36f,
        viewportHeight = 36f,
    ).apply {
        path(fill = SolidColor(Color(0xFF1D1B20))) {
            moveTo(10f, 26f)
            curveTo(9.45f, 26f, 8.975f, 25.808f, 8.575f, 25.425f)
            curveTo(8.192f, 25.025f, 8f, 24.55f, 8f, 24f)
            verticalLineTo(12f)
            curveTo(8f, 11.45f, 8.192f, 10.983f, 8.575f, 10.6f)
            curveTo(8.975f, 10.2f, 9.45f, 10f, 10f, 10f)
            horizontalLineTo(26f)
            curveTo(26.55f, 10f, 27.017f, 10.2f, 27.4f, 10.6f)
            curveTo(27.8f, 10.983f, 28f, 11.45f, 28f, 12f)
            verticalLineTo(24f)
            curveTo(28f, 24.55f, 27.8f, 25.025f, 27.4f, 25.425f)
            curveTo(27.017f, 25.808f, 26.55f, 26f, 26f, 26f)
            horizontalLineTo(10f)
            close()
            moveTo(18f, 19f)
            lineTo(26f, 14f)
            verticalLineTo(12f)
            lineTo(18f, 17f)
            lineTo(10f, 12f)
            verticalLineTo(14f)
            lineTo(18f, 19f)
            close()
        }
    }.build()
}
