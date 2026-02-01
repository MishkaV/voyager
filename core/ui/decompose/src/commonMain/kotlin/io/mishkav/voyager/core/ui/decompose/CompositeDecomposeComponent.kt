package io.mishkav.voyager.core.ui.decompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import io.mishkav.voyager.core.ui.decompose.back.backAnimation

abstract class CompositeDecomposeComponent<C : Any> : DecomposeComponent(), ComponentContext {
    protected val navigation = StackNavigation<C>()

    protected abstract val stack: Value<ChildStack<C, DecomposeComponent>>

    @Composable
    @Suppress("NonSkippableComposable")
    override fun Render() {
        val childStack by stack.subscribeAsState()

        ChildStack(
            stack = childStack,
            animation = backAnimation(
                backHandler = backHandler,
                onBack = navigation::pop,
            ),
        ) {
            it.instance.Render()
        }
    }
}
