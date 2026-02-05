package io.mishka.voyager.profile.api

import com.arkivanov.decompose.ComponentContext
import io.mishkav.voyager.core.ui.decompose.ScreenDecomposeComponent

abstract class ProfileComponent(
    componentContext: ComponentContext
) : ScreenDecomposeComponent(componentContext) {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
        ): ProfileComponent
    }
}
