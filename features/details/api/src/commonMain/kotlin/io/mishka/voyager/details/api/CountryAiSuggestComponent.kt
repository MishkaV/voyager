package io.mishka.voyager.details.api

import com.arkivanov.decompose.ComponentContext
import io.mishka.voyager.details.api.models.CountryAiSuggestArgs
import io.mishkav.voyager.core.ui.decompose.ScreenDecomposeComponent

abstract class CountryAiSuggestComponent(
    componentContext: ComponentContext
) : ScreenDecomposeComponent(componentContext) {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            args: CountryAiSuggestArgs,
        ): CountryAiSuggestComponent
    }
}
