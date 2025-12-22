package io.mishka.voyager.intro.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable

@Composable
actual fun VoyagerBackground(modifier: androidx.compose.ui.Modifier) {
    // Migrate with Coil GIF with KMP support
    // https://github.com/coil-kt/coil/pull/2594
    Box(modifier)
}
