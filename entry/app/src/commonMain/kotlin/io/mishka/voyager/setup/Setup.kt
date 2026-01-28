package io.mishka.voyager.setup

import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.request.crossfade

fun setupInstruments(isDebuggable: Boolean) {
    setupDebugInstruments(isDebuggable)
    setupCoil()
}

internal fun setupCoil() {
    SingletonImageLoader.setSafe { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .components {
                addCoilGifFactory()
            }
            .build()
    }
}
