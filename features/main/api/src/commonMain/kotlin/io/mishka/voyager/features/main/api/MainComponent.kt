package io.mishka.voyager.features.main.api

import com.arkivanov.decompose.ComponentContext
import io.mishkav.voyager.core.ui.decompose.CompositeDecomposeComponent
import io.mishkav.voyager.core.ui.decompose.DecomposeOnBackParameter

abstract class MainComponent<C : Any> : CompositeDecomposeComponent<C>() {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: DecomposeOnBackParameter,
        ): MainComponent<*>
    }
}
