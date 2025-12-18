package io.mishka.voyager.${__moduleName}.impl.api

<#if isMultiScreenNavigation>
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.backhandler.BackHandler
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.binding
import io.mishka.voyager.${__moduleName}.api.${__formattedModuleName}Component
import io.mishka.voyager.${__moduleName}.api.model.${__formattedModuleName}Config
import io.mishkav.voyager.core.ui.decompose.DecomposeComponent
import io.mishkav.voyager.core.ui.decompose.back.backAnimation

@AssistedInject
class ${__formattedModuleName}ComponentImpl(
    @Assisted componentContext: ComponentContext,
    @Assisted externalBackHandler: BackHandler?,
) : ${__formattedModuleName}Component<${__formattedModuleName}Config>(), ComponentContext by componentContext, BackHandlerOwner {

    override val stack: Value<ChildStack<${__formattedModuleName}Config, DecomposeComponent>> = childStack(
        source = navigation,
        serializer = ${__formattedModuleName}Config.serializer(),
        initialConfiguration = ${__formattedModuleName}Config.Screen,
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(
        config: ${__formattedModuleName}Config,
        componentContext: ComponentContext
    ): DecomposeComponent = when (config) {
        // TODO: Add your cases here
    }

    @AssistedFactory
    @ContributesBinding(AppScope::class, binding<${__formattedModuleName}Component.Factory>())
    interface Factory : ${__formattedModuleName}Component.Factory {
        override fun create(
            componentContext: ComponentContext,
            backHandler: BackHandler?
        ): ${__formattedModuleName}ComponentImpl
    }
}

<#else>
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.backhandler.BackHandler
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.compose.metroViewModel
import io.mishkav.voyager.core.ui.lifecycle.viewModelWithFactory
import io.mishka.voyager.${__moduleName}.api.${__formattedModuleName}Component
import io.mishka.voyager.${__moduleName}.impl.${__formattedModuleName}Screen
import io.mishka.voyager.${__moduleName}.impl.${__formattedModuleName}ViewModel

@AssistedInject
class ${__formattedModuleName}ComponentImpl(
    @Assisted componentContext: ComponentContext,
    @Assisted externalBackHandler: BackHandler?,
    private val ${__formattedModuleName?uncap_first}ViewModelProvider: Provider<${__formattedModuleName}ViewModel>,
) : ${__formattedModuleName}Component, ComponentContext by componentContext, BackHandlerOwner {

    @Composable
    override fun Render(modifier: Modifier) {
        val viewModel = viewModelWithFactory {
            ${__formattedModuleName?uncap_first}ViewModelProvider.get()
        }

        ${__formattedModuleName}Screen(
            viewModel = viewModel,
            modifier = modifier,
        )
    }

    @AssistedFactory
    @ContributesBinding(AppScope::class, binding<${__formattedModuleName}Component.Factory>())
    interface Factory : ${__formattedModuleName}Component.Factory {
        override fun create(
            componentContext: ComponentContext,
            backHandler: BackHandler?
        ): ${__formattedModuleName}ComponentImpl
    }
}
</#if>
