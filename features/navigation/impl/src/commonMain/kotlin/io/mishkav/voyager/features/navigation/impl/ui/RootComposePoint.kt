package io.mishkav.voyager.features.navigation.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import io.mishkav.voyager.core.ui.theme.LocalColors
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.features.navigation.api.LocalRootNavigation
import io.mishkav.voyager.features.navigation.api.RootComponent

@Composable
fun RootComposePoint(
    root: RootComponent,
    modifier: Modifier = Modifier,
) {
    VoyagerTheme {
        CompositionLocalProvider(
            LocalRootNavigation provides root
        ) {
            root.Render(
                modifier = modifier
                    .fillMaxSize()
                    .background(LocalColors.current.background),
            )
        }
    }
}
