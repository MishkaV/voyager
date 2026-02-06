package io.mishka.voyager.setup

import androidx.compose.runtime.Composer
import androidx.compose.runtime.tooling.ComposeStackTraceMode
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.request.crossfade
import io.github.jan.supabase.coil.Coil3Integration

fun setupInstruments(
    isDebuggable: Boolean,
    supabaseCoil: Coil3Integration,
) {
    setupDebugInstruments(isDebuggable)
    setupCoil(supabaseCoil)
    // Setup compose traces for prod builds
    Composer.setDiagnosticStackTraceMode(ComposeStackTraceMode.Auto)
}

internal fun setupCoil(supabaseCoil: Coil3Integration) {
    SingletonImageLoader.setSafe { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .components {
                add(supabaseCoil)
                addCoilGifFactory()
            }
            .build()
    }
}
