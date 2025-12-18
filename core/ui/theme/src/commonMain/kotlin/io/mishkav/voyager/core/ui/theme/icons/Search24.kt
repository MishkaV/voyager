package io.mishkav.voyager.core.ui.theme.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerIcon

val VoyagerIcon.search24: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Search24",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        group(
            clipPathData = PathData {
                moveTo(3f, 3f)
                horizontalLineToRelative(18f)
                verticalLineToRelative(18f)
                horizontalLineToRelative(-18f)
                close()
            }
        ) {
            path(fill = SolidColor(Color.Black)) {
                moveTo(10.545f, 2.989f)
                curveTo(10.595f, 2.989f, 10.595f, 2.989f, 10.645f, 2.989f)
                curveTo(11.116f, 2.991f, 11.573f, 3.005f, 12.035f, 3.105f)
                curveTo(12.08f, 3.115f, 12.08f, 3.115f, 12.125f, 3.124f)
                curveTo(13.141f, 3.333f, 14.106f, 3.734f, 14.953f, 4.336f)
                curveTo(14.976f, 4.352f, 15f, 4.368f, 15.023f, 4.385f)
                curveTo(16.584f, 5.479f, 17.69f, 7.175f, 18.054f, 9.048f)
                curveTo(18.132f, 9.509f, 18.167f, 9.966f, 18.166f, 10.433f)
                curveTo(18.166f, 10.474f, 18.166f, 10.474f, 18.166f, 10.516f)
                curveTo(18.16f, 12.31f, 17.484f, 14.094f, 16.254f, 15.41f)
                curveTo(16.301f, 15.515f, 16.352f, 15.587f, 16.434f, 15.667f)
                curveTo(16.453f, 15.686f, 16.472f, 15.705f, 16.492f, 15.724f)
                curveTo(16.535f, 15.764f, 16.579f, 15.804f, 16.624f, 15.842f)
                curveTo(16.837f, 16.024f, 17.034f, 16.221f, 17.231f, 16.42f)
                curveTo(17.294f, 16.483f, 17.356f, 16.546f, 17.419f, 16.609f)
                curveTo(17.575f, 16.765f, 17.73f, 16.921f, 17.886f, 17.078f)
                curveTo(18.018f, 17.21f, 18.15f, 17.343f, 18.282f, 17.475f)
                curveTo(18.343f, 17.537f, 18.405f, 17.598f, 18.466f, 17.66f)
                curveTo(18.637f, 17.832f, 18.808f, 18f, 18.993f, 18.157f)
                curveTo(19.169f, 18.313f, 19.333f, 18.483f, 19.499f, 18.649f)
                curveTo(19.54f, 18.69f, 19.581f, 18.732f, 19.623f, 18.773f)
                curveTo(19.708f, 18.858f, 19.794f, 18.944f, 19.88f, 19.03f)
                curveTo(19.99f, 19.139f, 20.1f, 19.249f, 20.21f, 19.358f)
                curveTo(20.295f, 19.443f, 20.379f, 19.528f, 20.464f, 19.612f)
                curveTo(20.505f, 19.653f, 20.545f, 19.693f, 20.586f, 19.733f)
                curveTo(20.642f, 19.79f, 20.699f, 19.846f, 20.755f, 19.903f)
                curveTo(20.787f, 19.935f, 20.819f, 19.967f, 20.853f, 20f)
                curveTo(21.003f, 20.167f, 21.016f, 20.296f, 21.011f, 20.516f)
                curveTo(20.994f, 20.668f, 20.955f, 20.739f, 20.86f, 20.859f)
                curveTo(20.685f, 20.998f, 20.547f, 21.016f, 20.327f, 21.019f)
                curveTo(20.061f, 20.969f, 19.886f, 20.779f, 19.705f, 20.593f)
                curveTo(19.679f, 20.567f, 19.654f, 20.541f, 19.628f, 20.515f)
                curveTo(19.546f, 20.433f, 19.466f, 20.351f, 19.385f, 20.268f)
                curveTo(19.305f, 20.187f, 19.224f, 20.105f, 19.143f, 20.023f)
                curveTo(19.093f, 19.972f, 19.043f, 19.921f, 18.993f, 19.871f)
                curveTo(18.884f, 19.76f, 18.773f, 19.652f, 18.655f, 19.551f)
                curveTo(18.469f, 19.391f, 18.298f, 19.215f, 18.125f, 19.041f)
                curveTo(18.062f, 18.978f, 17.999f, 18.915f, 17.937f, 18.852f)
                curveTo(17.781f, 18.696f, 17.625f, 18.54f, 17.47f, 18.383f)
                curveTo(17.338f, 18.251f, 17.206f, 18.118f, 17.074f, 17.986f)
                curveTo(17.012f, 17.924f, 16.951f, 17.862f, 16.89f, 17.801f)
                curveTo(16.715f, 17.625f, 16.539f, 17.453f, 16.35f, 17.292f)
                curveTo(16.25f, 17.204f, 16.156f, 17.109f, 16.062f, 17.014f)
                curveTo(16.042f, 16.994f, 16.022f, 16.974f, 16.001f, 16.954f)
                curveTo(15.939f, 16.891f, 15.877f, 16.829f, 15.815f, 16.766f)
                curveTo(15.772f, 16.723f, 15.729f, 16.68f, 15.686f, 16.637f)
                curveTo(15.583f, 16.533f, 15.479f, 16.429f, 15.375f, 16.324f)
                curveTo(15.231f, 16.379f, 15.118f, 16.447f, 14.993f, 16.537f)
                curveTo(14.134f, 17.143f, 13.198f, 17.534f, 12.176f, 17.766f)
                curveTo(12.143f, 17.773f, 12.111f, 17.781f, 12.077f, 17.788f)
                curveTo(11.143f, 17.995f, 10.054f, 18.002f, 9.117f, 17.801f)
                curveTo(9.089f, 17.795f, 9.06f, 17.789f, 9.03f, 17.784f)
                curveTo(7.156f, 17.411f, 5.437f, 16.308f, 4.336f, 14.742f)
                curveTo(4.322f, 14.722f, 4.308f, 14.703f, 4.294f, 14.682f)
                curveTo(3.519f, 13.58f, 3.006f, 12.207f, 2.992f, 10.85f)
                curveTo(2.991f, 10.822f, 2.991f, 10.793f, 2.991f, 10.764f)
                curveTo(2.97f, 8.665f, 3.618f, 6.792f, 5.092f, 5.268f)
                curveTo(5.133f, 5.227f, 5.174f, 5.186f, 5.215f, 5.145f)
                curveTo(5.235f, 5.125f, 5.254f, 5.105f, 5.274f, 5.085f)
                curveTo(5.764f, 4.598f, 6.326f, 4.216f, 6.931f, 3.886f)
                curveTo(6.979f, 3.86f, 7.027f, 3.833f, 7.075f, 3.806f)
                curveTo(7.737f, 3.428f, 8.542f, 3.186f, 9.293f, 3.07f)
                curveTo(9.348f, 3.061f, 9.348f, 3.061f, 9.404f, 3.052f)
                curveTo(9.785f, 2.994f, 10.16f, 2.987f, 10.545f, 2.989f)
                close()
                moveTo(5.753f, 6.3f)
                curveTo(5.714f, 6.348f, 5.675f, 6.397f, 5.637f, 6.445f)
                curveTo(5.615f, 6.473f, 5.615f, 6.473f, 5.592f, 6.501f)
                curveTo(4.982f, 7.267f, 4.546f, 8.127f, 4.329f, 9.084f)
                curveTo(4.321f, 9.12f, 4.321f, 9.12f, 4.313f, 9.157f)
                curveTo(4.222f, 9.58f, 4.182f, 9.998f, 4.182f, 10.431f)
                curveTo(4.182f, 10.455f, 4.182f, 10.48f, 4.182f, 10.505f)
                curveTo(4.183f, 10.966f, 4.236f, 11.409f, 4.336f, 11.859f)
                curveTo(4.341f, 11.884f, 4.347f, 11.909f, 4.352f, 11.935f)
                curveTo(4.583f, 13.007f, 5.184f, 13.977f, 5.922f, 14.773f)
                curveTo(5.942f, 14.794f, 5.962f, 14.816f, 5.982f, 14.838f)
                curveTo(6.217f, 15.086f, 6.474f, 15.297f, 6.751f, 15.496f)
                curveTo(6.792f, 15.526f, 6.792f, 15.526f, 6.835f, 15.556f)
                curveTo(8.267f, 16.572f, 9.99f, 16.921f, 11.717f, 16.65f)
                curveTo(12.654f, 16.487f, 13.555f, 16.117f, 14.32f, 15.551f)
                curveTo(14.358f, 15.523f, 14.395f, 15.496f, 14.433f, 15.468f)
                curveTo(14.792f, 15.201f, 15.124f, 14.911f, 15.41f, 14.566f)
                curveTo(15.437f, 14.535f, 15.464f, 14.504f, 15.492f, 14.472f)
                curveTo(16.082f, 13.788f, 16.499f, 12.972f, 16.746f, 12.106f)
                curveTo(16.755f, 12.074f, 16.764f, 12.043f, 16.774f, 12.011f)
                curveTo(17.168f, 10.63f, 16.996f, 9.062f, 16.36f, 7.781f)
                curveTo(16.347f, 7.757f, 16.335f, 7.732f, 16.323f, 7.707f)
                curveTo(15.945f, 6.946f, 15.427f, 6.258f, 14.778f, 5.707f)
                curveTo(14.755f, 5.688f, 14.732f, 5.668f, 14.708f, 5.648f)
                curveTo(12.11f, 3.476f, 8.309f, 3.776f, 5.753f, 6.3f)
                close()
            }
        }
    }.build()
}
