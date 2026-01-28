package io.mishka.voyager.auth.impl.api

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import io.mishkav.voyager.core.ui.decompose.ScreenDecomposeComponent

@AssistedInject
class AskEmailDecomposeComponent(
    @Assisted componentContext: ComponentContext
) : ScreenDecomposeComponent(componentContext) {

    @Composable
    @Suppress("NonSkippableComposable")
    override fun Render() {
        // TODO Implement your UI
    }

    @AssistedFactory
    interface Factory {
        fun create(
            componentContext: ComponentContext
        ): AskEmailDecomposeComponent
    }
}
