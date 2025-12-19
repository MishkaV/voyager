package io.mishka.voyager.intro.api

import com.arkivanov.decompose.ComponentContext
import io.mishkav.voyager.core.ui.decompose.ScreenDecomposeComponent

abstract class IntroComponent(
    componentContext: ComponentContext
) : ScreenDecomposeComponent(componentContext) {

    interface Factory {
        fun create(
            componentContext: ComponentContext,
        ): IntroComponent
    }
}
