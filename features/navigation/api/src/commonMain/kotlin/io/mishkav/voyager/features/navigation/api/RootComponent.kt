package io.mishkav.voyager.features.navigation.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackHandler

interface RootComponent : ComponentContext, RootNavigationInterface {

    @Composable
    fun Render(modifier: Modifier = Modifier)

    fun goBack()

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            backHandler: BackHandler?,
        ): RootComponent
    }
}
