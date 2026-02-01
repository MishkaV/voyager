package io.mishka.voyager.auth.impl.api

import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.binding
import io.mishka.voyager.auth.api.AuthComponent
import io.mishka.voyager.auth.api.model.AuthConfig
import io.mishkav.voyager.core.ui.decompose.DecomposeComponent

@AssistedInject
class AuthComponentImpl(
    @Assisted componentContext: ComponentContext,
    private val loginComponentFactory: LoginDecomposeComponent.Factory,
    private val askEmailDecomposeComponent: AskEmailDecomposeComponent.Factory,
    private val insertOTPDecomposeComponent: InsertOTPDecomposeComponent.Factory,
) : AuthComponent<AuthConfig>(), ComponentContext by componentContext, BackHandlerOwner {

    override val stack: Value<ChildStack<AuthConfig, DecomposeComponent>> = childStack(
        source = navigation,
        serializer = AuthConfig.serializer(),
        initialConfiguration = AuthConfig.Login,
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(
        config: AuthConfig,
        componentContext: ComponentContext
    ): DecomposeComponent = when (config) {
        AuthConfig.Login -> loginComponentFactory.create(
            componentContext = componentContext,
            navigateToAskEmail = {
                navigation.pushNew(AuthConfig.AskEmail)
            }
        )

        AuthConfig.AskEmail -> askEmailDecomposeComponent.create(
            componentContext = componentContext,
            navigateToOTP = {
                navigation.pushNew(AuthConfig.InsertOTP)
            },
            navigateBack = navigation::pop
        )

        AuthConfig.InsertOTP -> insertOTPDecomposeComponent.create(componentContext)
    }

    @AssistedFactory
    @ContributesBinding(AppScope::class, binding<AuthComponent.Factory>())
    interface Factory : AuthComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
        ): AuthComponentImpl
    }
}
