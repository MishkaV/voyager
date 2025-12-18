package io.mishkav.voyager.core.ui.theme.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerIcon

val VoyagerIcon.error36: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Error",
        defaultWidth = 36.dp,
        defaultHeight = 36.dp,
        viewportWidth = 36f,
        viewportHeight = 36f
    ).apply {
        path(
            fill = SolidColor(Color.White),
            pathFillType = PathFillType.EvenOdd
        ) {
            moveTo(18f, 8.025f)
            curveTo(17.129f, 8.025f, 16.035f, 8.623f, 15.095f, 10.318f)
            lineTo(8.025f, 23.035f)
            curveTo(7.151f, 24.611f, 7.204f, 25.824f, 7.649f, 26.583f)
            curveTo(8.095f, 27.342f, 9.126f, 27.978f, 10.93f, 27.978f)
            horizontalLineTo(18f)
            curveTo(18.052f, 27.978f, 18.103f, 27.982f, 18.153f, 27.99f)
            horizontalLineTo(25.07f)
            curveTo(26.868f, 27.99f, 27.9f, 27.354f, 28.347f, 26.594f)
            curveTo(28.794f, 25.834f, 28.849f, 24.622f, 27.976f, 23.047f)
            lineTo(24.336f, 16.491f)
            lineTo(20.906f, 10.319f)
            curveTo(19.966f, 8.623f, 18.871f, 8.025f, 18f, 8.025f)
            close()
            moveTo(17.847f, 29.978f)
            horizontalLineTo(10.93f)
            curveTo(8.686f, 29.978f, 6.847f, 29.168f, 5.925f, 27.596f)
            curveTo(5.002f, 26.025f, 5.189f, 24.024f, 6.275f, 22.065f)
            lineTo(13.345f, 9.348f)
            curveTo(13.345f, 9.349f, 13.346f, 9.348f, 13.345f, 9.348f)
            curveTo(14.482f, 7.299f, 16.129f, 6.025f, 18f, 6.025f)
            curveTo(19.871f, 6.025f, 21.518f, 7.299f, 22.654f, 9.348f)
            curveTo(22.654f, 9.348f, 22.654f, 9.348f, 22.654f, 9.348f)
            lineTo(26.084f, 15.519f)
            lineTo(29.724f, 22.076f)
            curveTo(30.811f, 24.036f, 30.994f, 26.038f, 30.071f, 27.608f)
            curveTo(29.147f, 29.179f, 27.309f, 29.99f, 25.07f, 29.99f)
            horizontalLineTo(18f)
            curveTo(17.948f, 29.99f, 17.897f, 29.986f, 17.847f, 29.978f)
            close()
            moveTo(18f, 13.5f)
            curveTo(18.552f, 13.5f, 19f, 13.948f, 19f, 14.5f)
            verticalLineTo(20.333f)
            curveTo(19f, 20.886f, 18.552f, 21.333f, 18f, 21.333f)
            curveTo(17.448f, 21.333f, 17f, 20.886f, 17f, 20.333f)
            verticalLineTo(14.5f)
            curveTo(17f, 13.948f, 17.448f, 13.5f, 18f, 13.5f)
            close()
        }
        path(
            fill = SolidColor(Color.White),
            pathFillType = PathFillType.EvenOdd
        ) {
            moveTo(16.994f, 23.833f)
            curveTo(16.994f, 23.281f, 17.442f, 22.833f, 17.994f, 22.833f)
            horizontalLineTo(18.005f)
            curveTo(18.557f, 22.833f, 19.005f, 23.281f, 19.005f, 23.833f)
            curveTo(19.005f, 24.386f, 18.557f, 24.833f, 18.005f, 24.833f)
            horizontalLineTo(17.994f)
            curveTo(17.442f, 24.833f, 16.994f, 24.386f, 16.994f, 23.833f)
            close()
        }
    }.build()
}
