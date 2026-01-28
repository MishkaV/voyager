package io.mishka.voyager.setup

import coil3.ComponentRegistry
import coil3.gif.AnimatedImageDecoder

actual fun ComponentRegistry.Builder.addCoilGifFactory() {
    add(AnimatedImageDecoder.Factory())
}
