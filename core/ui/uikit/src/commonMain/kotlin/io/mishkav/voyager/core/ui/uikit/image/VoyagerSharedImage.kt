package io.mishkav.voyager.core.ui.uikit.image

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
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
import io.mishkav.voyager.core.ui.uikit.transition.LocalNavAnimatedVisibilityScope

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
public fun SharedTransitionScope.VoyagerSharedImage(
    data: Any?,
    contentDescription: String?,
    shapeDp: Dp,
    shareKey: String,
    modifier: Modifier = Modifier,
    imageBuilder: ImageRequest.Builder = ImageRequest.Builder(LocalPlatformContext.current),
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    VoyagerSharedImage(
        data = data,
        contentDescription = contentDescription,
        shape = RoundedCornerShape(shapeDp),
        shareKey = shareKey,
        modifier = modifier,
        imageBuilder = imageBuilder,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
public fun SharedTransitionScope.VoyagerSharedImage(
    data: Any?,
    contentDescription: String?,
    shape: CornerBasedShape,
    shareKey: String,
    modifier: Modifier = Modifier,
    imageBuilder: ImageRequest.Builder = ImageRequest.Builder(LocalPlatformContext.current),
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    val animationScope = LocalNavAnimatedVisibilityScope.current
    val animationSpec = tween<Rect>(durationMillis = 500)

    val imagePainter = rememberAsyncImagePainter(
        model = imageBuilder
            .data(data)
            .crossfade(true)
            .build()
    )

    val imageModelState = imagePainter.state.collectAsState()
    val isShimmerVisible = remember {
        derivedStateOf { imageModelState.value is AsyncImagePainter.State.Loading }
    }

    Box(modifier = modifier) {
        if (imageModelState.value !is AsyncImagePainter.State.Error) {
            Image(
                modifier = Modifier
                    .placeholderFadeConnecting(
                        shape = shape,
                        visible = isShimmerVisible.value,
                    )
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = shareKey),
                        animatedVisibilityScope = animationScope,
                        boundsTransform = { _, _ -> animationSpec }
                    )
                    .fillMaxSize()
                    .clip(shape),
                painter = imagePainter,
                contentScale = contentScale,
                alignment = alignment,
                alpha = alpha,
                colorFilter = colorFilter,
                contentDescription = contentDescription,
            )
        } else {
            ErrorProgress(
                modifier = Modifier
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = shareKey),
                        animatedVisibilityScope = animationScope,
                        boundsTransform = { _, _ -> animationSpec }
                    )
                    .fillMaxSize()
                    .clip(shape)
            )
        }
    }
}
