package io.mishkav.voyager.core.ui.theme.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerIcon

val VoyagerIcon.prev24: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Prev24",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        group(
            clipPathData = PathData {
                moveTo(0f, 0f)
                horizontalLineToRelative(24f)
                verticalLineToRelative(24f)
                horizontalLineToRelative(-24f)
                close()
            }
        ) {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1.00088f,
                strokeLineJoin = StrokeJoin.Round,
            ) {
                moveTo(4.272f, 9.929f)
                curveTo(4.752f, 8.142f, 5.836f, 6.575f, 7.34f, 5.497f)
                curveTo(8.845f, 4.419f, 10.676f, 3.896f, 12.523f, 4.017f)
                curveTo(14.37f, 4.138f, 16.118f, 4.896f, 17.469f, 6.161f)
                curveTo(18.819f, 7.426f, 19.69f, 9.121f, 19.931f, 10.956f)
                curveTo(20.173f, 12.791f, 19.771f, 14.653f, 18.794f, 16.225f)
                curveTo(17.816f, 17.796f, 16.324f, 18.98f, 14.571f, 19.576f)
                curveTo(12.819f, 20.17f, 10.914f, 20.139f, 9.182f, 19.487f)
                curveTo(7.45f, 18.835f, 5.997f, 17.603f, 5.072f, 16f)
            }
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1.00088f,
                strokeLineJoin = StrokeJoin.Round,
            ) {
                moveTo(2.965f, 6.136f)
                lineTo(4f, 10f)
                lineTo(7.864f, 8.965f)
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(9.958f, 15f)
                curveTo(9.905f, 15f, 9.859f, 14.984f, 9.822f, 14.952f)
                curveTo(9.79f, 14.915f, 9.774f, 14.869f, 9.774f, 14.816f)
                verticalLineTo(10.336f)
                lineTo(8.446f, 11.36f)
                curveTo(8.403f, 11.392f, 8.358f, 11.405f, 8.31f, 11.4f)
                curveTo(8.262f, 11.395f, 8.222f, 11.371f, 8.19f, 11.328f)
                lineTo(7.982f, 11.064f)
                curveTo(7.95f, 11.016f, 7.937f, 10.968f, 7.942f, 10.92f)
                curveTo(7.953f, 10.872f, 7.979f, 10.832f, 8.022f, 10.8f)
                lineTo(9.766f, 9.456f)
                curveTo(9.803f, 9.429f, 9.838f, 9.413f, 9.87f, 9.408f)
                curveTo(9.902f, 9.403f, 9.937f, 9.4f, 9.974f, 9.4f)
                horizontalLineTo(10.382f)
                curveTo(10.435f, 9.4f, 10.478f, 9.419f, 10.51f, 9.456f)
                curveTo(10.542f, 9.488f, 10.558f, 9.531f, 10.558f, 9.584f)
                verticalLineTo(14.816f)
                curveTo(10.558f, 14.869f, 10.542f, 14.915f, 10.51f, 14.952f)
                curveTo(10.478f, 14.984f, 10.435f, 15f, 10.382f, 15f)
                horizontalLineTo(9.958f)
                close()
                moveTo(13.708f, 15.08f)
                curveTo(13.34f, 15.08f, 13.028f, 15.024f, 12.772f, 14.912f)
                curveTo(12.521f, 14.795f, 12.316f, 14.635f, 12.156f, 14.432f)
                curveTo(11.996f, 14.229f, 11.878f, 13.997f, 11.804f, 13.736f)
                curveTo(11.734f, 13.475f, 11.694f, 13.197f, 11.684f, 12.904f)
                curveTo(11.679f, 12.76f, 11.673f, 12.608f, 11.668f, 12.448f)
                curveTo(11.668f, 12.288f, 11.668f, 12.128f, 11.668f, 11.968f)
                curveTo(11.673f, 11.803f, 11.679f, 11.645f, 11.684f, 11.496f)
                curveTo(11.689f, 11.203f, 11.729f, 10.925f, 11.804f, 10.664f)
                curveTo(11.884f, 10.397f, 12.001f, 10.165f, 12.156f, 9.968f)
                curveTo(12.316f, 9.765f, 12.524f, 9.608f, 12.78f, 9.496f)
                curveTo(13.036f, 9.379f, 13.345f, 9.32f, 13.708f, 9.32f)
                curveTo(14.076f, 9.32f, 14.385f, 9.379f, 14.636f, 9.496f)
                curveTo(14.892f, 9.608f, 15.1f, 9.765f, 15.26f, 9.968f)
                curveTo(15.42f, 10.165f, 15.537f, 10.397f, 15.612f, 10.664f)
                curveTo(15.692f, 10.925f, 15.734f, 11.203f, 15.74f, 11.496f)
                curveTo(15.745f, 11.645f, 15.748f, 11.803f, 15.748f, 11.968f)
                curveTo(15.753f, 12.128f, 15.753f, 12.288f, 15.748f, 12.448f)
                curveTo(15.748f, 12.608f, 15.745f, 12.76f, 15.74f, 12.904f)
                curveTo(15.734f, 13.197f, 15.692f, 13.475f, 15.612f, 13.736f)
                curveTo(15.537f, 13.997f, 15.42f, 14.229f, 15.26f, 14.432f)
                curveTo(15.105f, 14.635f, 14.9f, 14.795f, 14.644f, 14.912f)
                curveTo(14.393f, 15.024f, 14.081f, 15.08f, 13.708f, 15.08f)
                close()
                moveTo(13.708f, 14.4f)
                curveTo(14.124f, 14.4f, 14.431f, 14.264f, 14.628f, 13.992f)
                curveTo(14.83f, 13.715f, 14.934f, 13.339f, 14.94f, 12.864f)
                curveTo(14.95f, 12.709f, 14.956f, 12.56f, 14.956f, 12.416f)
                curveTo(14.956f, 12.267f, 14.956f, 12.12f, 14.956f, 11.976f)
                curveTo(14.956f, 11.827f, 14.95f, 11.68f, 14.94f, 11.536f)
                curveTo(14.934f, 11.072f, 14.83f, 10.701f, 14.628f, 10.424f)
                curveTo(14.431f, 10.141f, 14.124f, 10f, 13.708f, 10f)
                curveTo(13.297f, 10f, 12.991f, 10.141f, 12.788f, 10.424f)
                curveTo(12.59f, 10.701f, 12.486f, 11.072f, 12.476f, 11.536f)
                curveTo(12.476f, 11.68f, 12.473f, 11.827f, 12.468f, 11.976f)
                curveTo(12.468f, 12.12f, 12.468f, 12.267f, 12.468f, 12.416f)
                curveTo(12.473f, 12.56f, 12.476f, 12.709f, 12.476f, 12.864f)
                curveTo(12.486f, 13.339f, 12.593f, 13.715f, 12.796f, 13.992f)
                curveTo(12.998f, 14.264f, 13.302f, 14.4f, 13.708f, 14.4f)
                close()
            }
        }
    }.build()
}
