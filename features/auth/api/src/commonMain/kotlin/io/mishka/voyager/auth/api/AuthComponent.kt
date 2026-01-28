package io.mishka.voyager.auth.api

import com.arkivanov.decompose.ComponentContext
import io.mishkav.voyager.core.ui.decompose.CompositeDecomposeComponent

abstract class AuthComponent<C : Any> : CompositeDecomposeComponent<C>() {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
        ): AuthComponent<*>
    }
}
