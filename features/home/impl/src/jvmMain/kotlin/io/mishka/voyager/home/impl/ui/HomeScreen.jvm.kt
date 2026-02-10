package io.mishka.voyager.home.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal actual fun MapboxMapView(
    viewModel: HomeViewModel,
    modifier: Modifier
) {
    // Not supported on JVM/Desktop
    Box(modifier)
}
