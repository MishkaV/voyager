package io.mishkav.voyager.core.ui.theme.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerIcon

val VoyagerIcon.apple36: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Apple36",
        defaultWidth = 36.dp,
        defaultHeight = 36.dp,
        viewportWidth = 36f,
        viewportHeight = 36f,
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(18.131f, 9.981f)
            curveTo(19.222f, 9.981f, 20.59f, 9.256f, 21.405f, 8.29f)
            curveTo(22.143f, 7.414f, 22.681f, 6.191f, 22.681f, 4.968f)
            curveTo(22.681f, 4.802f, 22.666f, 4.636f, 22.635f, 4.5f)
            curveTo(21.421f, 4.545f, 19.96f, 5.3f, 19.084f, 6.312f)
            curveTo(18.392f, 7.082f, 17.762f, 8.29f, 17.762f, 9.528f)
            curveTo(17.762f, 9.709f, 17.793f, 9.89f, 17.808f, 9.951f)
            curveTo(17.885f, 9.966f, 18.008f, 9.981f, 18.131f, 9.981f)
            close()
            moveTo(14.288f, 28.25f)
            curveTo(15.779f, 28.25f, 16.44f, 27.269f, 18.3f, 27.269f)
            curveTo(20.191f, 27.269f, 20.606f, 28.22f, 22.266f, 28.22f)
            curveTo(23.896f, 28.22f, 24.987f, 26.74f, 26.017f, 25.291f)
            curveTo(27.17f, 23.63f, 27.647f, 21.999f, 27.677f, 21.924f)
            curveTo(27.57f, 21.893f, 24.449f, 20.64f, 24.449f, 17.122f)
            curveTo(24.449f, 14.073f, 26.909f, 12.698f, 27.047f, 12.593f)
            curveTo(25.418f, 10.298f, 22.943f, 10.237f, 22.266f, 10.237f)
            curveTo(20.437f, 10.237f, 18.946f, 11.325f, 18.008f, 11.325f)
            curveTo(16.993f, 11.325f, 15.656f, 10.298f, 14.072f, 10.298f)
            curveTo(11.059f, 10.298f, 8f, 12.744f, 8f, 17.364f)
            curveTo(8f, 20.233f, 9.138f, 23.267f, 10.537f, 25.23f)
            curveTo(11.736f, 26.891f, 12.781f, 28.25f, 14.288f, 28.25f)
            close()
        }
    }.build()
}
