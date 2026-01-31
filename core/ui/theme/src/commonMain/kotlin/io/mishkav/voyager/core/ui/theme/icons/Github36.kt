package io.mishkav.voyager.core.ui.theme.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerIcon

val VoyagerIcon.github36: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Github36",
        defaultWidth = 36.dp,
        defaultHeight = 36.dp,
        viewportWidth = 36f,
        viewportHeight = 36f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF1F2328)),
            pathFillType = PathFillType.EvenOdd
        ) {
            moveTo(18f, 6.784f)
            curveTo(11.646f, 6.784f, 6.5f, 11.93f, 6.5f, 18.284f)
            curveTo(6.5f, 23.372f, 9.792f, 27.671f, 14.363f, 29.194f)
            curveTo(14.938f, 29.295f, 15.154f, 28.95f, 15.154f, 28.648f)
            curveTo(15.154f, 28.375f, 15.139f, 27.469f, 15.139f, 26.506f)
            curveTo(12.25f, 27.038f, 11.502f, 25.802f, 11.273f, 25.155f)
            curveTo(11.143f, 24.824f, 10.583f, 23.804f, 10.094f, 23.531f)
            curveTo(9.691f, 23.315f, 9.116f, 22.783f, 10.079f, 22.769f)
            curveTo(10.985f, 22.754f, 11.632f, 23.602f, 11.847f, 23.947f)
            curveTo(12.882f, 25.687f, 14.536f, 25.198f, 15.197f, 24.896f)
            curveTo(15.297f, 24.149f, 15.599f, 23.646f, 15.93f, 23.358f)
            curveTo(13.371f, 23.071f, 10.698f, 22.079f, 10.698f, 17.68f)
            curveTo(10.698f, 16.429f, 11.143f, 15.394f, 11.876f, 14.589f)
            curveTo(11.761f, 14.302f, 11.359f, 13.123f, 11.991f, 11.542f)
            curveTo(11.991f, 11.542f, 12.954f, 11.24f, 15.154f, 12.721f)
            curveTo(16.074f, 12.462f, 17.051f, 12.332f, 18.029f, 12.332f)
            curveTo(19.006f, 12.332f, 19.984f, 12.462f, 20.904f, 12.721f)
            curveTo(23.103f, 11.226f, 24.066f, 11.542f, 24.066f, 11.542f)
            curveTo(24.699f, 13.123f, 24.296f, 14.302f, 24.181f, 14.589f)
            curveTo(24.914f, 15.394f, 25.36f, 16.415f, 25.36f, 17.68f)
            curveTo(25.36f, 22.093f, 22.672f, 23.071f, 20.113f, 23.358f)
            curveTo(20.53f, 23.717f, 20.889f, 24.407f, 20.889f, 25.486f)
            curveTo(20.889f, 27.024f, 20.875f, 28.26f, 20.875f, 28.648f)
            curveTo(20.875f, 28.95f, 21.091f, 29.309f, 21.666f, 29.194f)
            curveTo(26.208f, 27.671f, 29.5f, 23.358f, 29.5f, 18.284f)
            curveTo(29.5f, 11.93f, 24.354f, 6.784f, 18f, 6.784f)
            verticalLineTo(6.784f)
            close()
        }
    }.build()
}
