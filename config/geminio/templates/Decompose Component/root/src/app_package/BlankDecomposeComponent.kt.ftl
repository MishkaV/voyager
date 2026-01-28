package ${packageName}

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import io.mishkav.voyager.core.ui.decompose.DecomposeComponent

@AssistedInject
class ${componentName}DecomposeComponent(
    @Assisted componentContext: ComponentContext
) : ComponentContext by componentContext, DecomposeComponent {

    @Composable
    @Suppress("NonSkippableComposable")
    override fun Render() {
        // TODO Implement your UI
    }

    @AssistedFactory
    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext
        ): ${componentName}DecomposeComponent
    }
}