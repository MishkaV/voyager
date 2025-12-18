package io.mishka.voyager.${__moduleName}.api

<#if isMultiScreenNavigation>
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackHandler
import io.mishkav.voyager.core.ui.decompose.CompositeDecomposeComponent

/**
 * Component interface for ${__formattedModuleName} feature with multi-screen navigation.
 */
abstract class ${__formattedModuleName}Component<C : Any> : CompositeDecomposeComponent<C>() {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            backHandler: BackHandler?
        ): ${__formattedModuleName}Component<*>
    }
}

<#else>
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackHandler
import io.mishkav.voyager.core.ui.decompose.DecomposeComponent

/**
 * Component interface for ${__formattedModuleName} feature.
 */
interface ${__formattedModuleName}Component : DecomposeComponent {

    @Composable
    override fun Render(modifier: Modifier)

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            backHandler: BackHandler?
        ): ${__formattedModuleName}Component
    }
}
</#if>
