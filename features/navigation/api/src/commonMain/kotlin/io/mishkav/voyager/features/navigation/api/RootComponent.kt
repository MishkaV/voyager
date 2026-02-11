package io.mishkav.voyager.features.navigation.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackHandler
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarComponent
import io.mishkav.voyager.features.navigation.api.model.VoyagerStartupStatus
import io.mishkav.voyager.features.navigation.api.snackbar.MainSnackbarState

interface RootComponent : ComponentContext, RootNavigationInterface {

    val bottomSnackbarController: SnackbarComponent<MainSnackbarState>

    val topSnackbarController: SnackbarComponent<MainSnackbarState>

    @Composable
    fun Render(modifier: Modifier = Modifier)

    fun goBack()

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            backHandler: BackHandler?,
            startupStatus: VoyagerStartupStatus,
        ): RootComponent
    }
}
