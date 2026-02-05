package io.mishka.voyager.search.api

import com.arkivanov.decompose.ComponentContext
import io.mishkav.voyager.core.ui.decompose.ScreenDecomposeComponent

abstract class SearchComponent(
    componentContext: ComponentContext
) : ScreenDecomposeComponent(componentContext) {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
        ): SearchComponent
    }
}
