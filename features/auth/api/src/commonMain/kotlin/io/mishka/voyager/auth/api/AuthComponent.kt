package io.mishka.voyager.auth.api

import com.arkivanov.decompose.ComponentContext
import io.mishkav.voyager.core.ui.decompose.CompositeDecomposeComponent
import io.mishkav.voyager.features.navigation.api.model.RootConfig

abstract class AuthComponent<C : Any> : CompositeDecomposeComponent<C>() {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            successNavigationConfig: RootConfig,
        ): AuthComponent<*>
    }
}
