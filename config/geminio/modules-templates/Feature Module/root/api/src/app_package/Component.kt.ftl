package io.mishka.voyager.${__moduleName}.api

<#if isMultiScreenNavigation>
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackHandler
import io.mishkav.voyager.core.ui.decompose.CompositeDecomposeComponent

abstract class ${__formattedModuleName}Component<C : Any> : CompositeDecomposeComponent<C>() {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            backHandler: BackHandler?
        ): ${__formattedModuleName}Component<*>
    }
}

<#else>
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackHandler
import io.mishkav.voyager.core.ui.decompose.ScreenDecomposeComponent

abstract class ${__formattedModuleName}Component(
    componentContext: ComponentContext
) : ScreenDecomposeComponent(componentContext) {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            backHandler: BackHandler?
        ): ${__formattedModuleName}Component
    }
}
</#if>
