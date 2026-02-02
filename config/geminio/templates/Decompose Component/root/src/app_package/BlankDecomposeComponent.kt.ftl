package ${packageName}

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.binding
import io.mishkav.voyager.core.ui.decompose.ScreenDecomposeComponent

@AssistedInject
class ${componentName}DecomposeComponent(
    @Assisted componentContext: ComponentContext
) : ScreenDecomposeComponent(componentContext) {

    @Composable
    @Suppress("NonSkippableComposable")
    override fun Render() {
        // TODO Implement your UI
    }

    @AssistedFactory
    interface Factory : ${__formattedModuleName}Component.Factory {
        override fun create(
            componentContext: ComponentContext,
        ): ${__formattedModuleName}ComponentImpl
    }
}