package io.mishka.voyager.setup

import androidx.compose.runtime.Composer
import androidx.compose.runtime.tooling.ComposeStackTraceMode
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.request.crossfade

fun setupInstruments(isDebuggable: Boolean) {
    setupDebugInstruments(isDebuggable)
    setupCoil()
    // Setup compose traces for prod builds
    Composer.setDiagnosticStackTraceMode(ComposeStackTraceMode.Auto)
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
