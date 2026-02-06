package io.mishka.voyager.features.main.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.router.stack.ChildStack
import io.mishka.voyager.features.main.impl.domain.model.MainBottomTab
import io.mishka.voyager.features.main.impl.domain.model.MainConfig
import io.mishka.voyager.features.main.impl.ui.bottombar.MainBottomBar
import io.mishkav.voyager.core.ui.decompose.DecomposeComponent

@Composable
fun MainScreen(
    childStack: ChildStack<MainConfig, DecomposeComponent>,
    onTabClick: (tab: MainBottomTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedTab = childStack.active.configuration

    Box(modifier = modifier.fillMaxSize()) {
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
