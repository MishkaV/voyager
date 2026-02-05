package io.mishka.voyager.home.api

import com.arkivanov.decompose.ComponentContext
import io.mishkav.voyager.core.ui.decompose.ScreenDecomposeComponent

abstract class HomeComponent(
    componentContext: ComponentContext
) : ScreenDecomposeComponent(componentContext) {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
        ): HomeComponent
    }
}
