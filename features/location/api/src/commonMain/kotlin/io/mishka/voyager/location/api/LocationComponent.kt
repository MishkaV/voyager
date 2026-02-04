package io.mishka.voyager.location.api

import com.arkivanov.decompose.ComponentContext
import io.mishkav.voyager.core.ui.decompose.ScreenDecomposeComponent

abstract class LocationComponent(
    componentContext: ComponentContext,
) : ScreenDecomposeComponent(componentContext) {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onRequestLocation: (isGranted: Boolean) -> Unit,
            onBack: () -> Unit,
        ): LocationComponent
    }
}
