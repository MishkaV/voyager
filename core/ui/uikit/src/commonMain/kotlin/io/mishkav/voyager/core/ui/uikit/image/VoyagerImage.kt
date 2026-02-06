package io.mishkav.voyager.core.ui.uikit.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import io.mishkav.voyager.core.ui.uikit.progress.ErrorProgress
import io.mishkav.voyager.core.ui.uikit.shimmer.placeholderFadeConnecting

@Composable
fun VoyagerImage(
    data: Any?,
    contentDescription: String?,
    shapeDp: Dp,
    modifier: Modifier = Modifier,
    imageBuilder: ImageRequest.Builder = ImageRequest.Builder(LocalPlatformContext.current),
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    VoyagerImage(
        data = data,
        contentDescription = contentDescription,
        shape = RoundedCornerShape(shapeDp),
        modifier = modifier,
        imageBuilder = imageBuilder,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
    )
}

@Composable
fun VoyagerImage(
    data: Any?,
    contentDescription: String?,
    shape: CornerBasedShape,
    modifier: Modifier = Modifier,
    imageBuilder: ImageRequest.Builder = ImageRequest.Builder(LocalPlatformContext.current),
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    val imagePainter = rememberAsyncImagePainter(
        model = imageBuilder
            .data(data)
            .crossfade(true)
            .build(),
    )
    val imageModelState = imagePainter.state.collectAsState()
    val isShimmerVisible = remember {
        derivedStateOf { imageModelState.value is AsyncImagePainter.State.Loading }
    }

    // Need box for more smooth transition
    Box(modifier = modifier) {
        when (imageModelState.value) {
            is AsyncImagePainter.State.Empty -> Unit
            is AsyncImagePainter.State.Error -> ErrorProgress()
            else -> {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .placeholderFadeConnecting(
                            shape = shape,
                            visible = isShimmerVisible.value,
                        ),
                    painter = imagePainter,
                    contentScale = contentScale,
                    alignment = alignment,
                    alpha = alpha,
                    colorFilter = colorFilter,
                    contentDescription = contentDescription,
                )
            }
        }
    }
}
