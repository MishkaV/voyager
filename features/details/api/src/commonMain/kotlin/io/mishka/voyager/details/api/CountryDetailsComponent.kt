package io.mishka.voyager.details.api

import com.arkivanov.decompose.ComponentContext
import io.mishka.voyager.details.api.models.CountryDetailsArgs
import io.mishkav.voyager.core.ui.decompose.DecomposeOnBackParameter
import io.mishkav.voyager.core.ui.decompose.ScreenDecomposeComponent

abstract class CountryDetailsComponent(
    componentContext: ComponentContext
) : ScreenDecomposeComponent(componentContext) {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            args: CountryDetailsArgs,
            navigateBack: DecomposeOnBackParameter,
        ): CountryDetailsComponent
    }
}
