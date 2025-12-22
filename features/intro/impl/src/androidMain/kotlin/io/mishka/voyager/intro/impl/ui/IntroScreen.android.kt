package io.mishka.voyager.intro.impl.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import org.jetbrains.compose.resources.ExperimentalResourceApi
import voyager.features.intro.impl.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun VoyagerBackground(modifier: Modifier) {
    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(Res.getUri("drawable/background.gif"))
            .size(Size.ORIGINAL)
            .build()
    )

    Image(
        modifier = modifier,
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}
