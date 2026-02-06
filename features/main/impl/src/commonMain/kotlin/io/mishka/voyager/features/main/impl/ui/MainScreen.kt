package io.mishka.voyager.features.main.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.router.stack.ChildStack
import io.mishka.voyager.features.main.api.snackbar.BottomMainSnackbarController
import io.mishka.voyager.features.main.api.snackbar.LocalBottomBottomMainSnackbarController
import io.mishka.voyager.features.main.impl.domain.model.MainBottomTab
import io.mishka.voyager.features.main.impl.domain.model.MainConfig
import io.mishka.voyager.features.main.impl.ui.bottombar.MainBottomBar
import io.mishkav.voyager.core.ui.decompose.DecomposeComponent
import io.mishkav.voyager.core.ui.uikit.snackbar.VoyagerSnackbar
import io.mishkav.voyager.core.ui.uikit.snackbar.compose.SnackbarBox

@Composable
fun MainScreen(
    bottomSnackbarController: BottomMainSnackbarController,
    childStack: ChildStack<MainConfig, DecomposeComponent>,
    onTabClick: (tab: MainBottomTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val selectedTab = childStack.active.configuration

    CompositionLocalProvider(
        LocalBottomBottomMainSnackbarController provides bottomSnackbarController,
    ) {
        SnackbarBox(
            modifier = modifier.fillMaxSize(),
            component = bottomSnackbarController,
            alignment = Alignment.BottomCenter,
            snackbarContent = { state ->
                VoyagerSnackbar(
                    text = state.text,
                    buttonText = state.buttonText,
                    buttonClick = bottomSnackbarController::hide,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 12.dp),
                )
            },
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Children(
                    modifier = Modifier.fillMaxSize(),
                    stack = childStack,
                ) {
                    it.instance.Render()
                }

                MainBottomBar(
                    selectedTab = selectedTab.tab,
                    onTabClick = onTabClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                )
            }
        }
    }
}
