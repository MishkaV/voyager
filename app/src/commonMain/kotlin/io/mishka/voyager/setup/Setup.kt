package io.mishka.voyager.setup

import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.request.crossfade

internal fun setupInstruments() {
    setupDebugInstruments()
    setupCoil()
}

internal fun setupCoil() {
    SingletonImageLoader.setSafe { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .build()
    }
}
