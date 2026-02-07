package io.mishka.voyager.details.impl.ui.blocks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.theme.icons.back16
import io.mishkav.voyager.core.ui.theme.icons.location24
import io.mishkav.voyager.core.ui.uikit.utils.clickableUnindicated

internal fun LazyListScope.appBarBlock(
    isVisited: Boolean,
    navigateBack: () -> Unit,
    addOrRemoveVisitedCounty: (isVisited: Boolean) -> Unit,
) {
    item(
        key = "APP_BAR_KEY",
        contentType = "APP_BAR_CONTENT_TYPE"
    ) {
        AppBarBlock(
            isVisited = isVisited,
            navigateBack = navigateBack,
            addOrRemoveVisitedCounty = addOrRemoveVisitedCounty,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun AppBarBlock(
    isVisited: Boolean,
    navigateBack: () -> Unit,
    addOrRemoveVisitedCounty: (isVisited: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(top = 16.dp),
    ) {
        Icon(
            imageVector = VoyagerTheme.icons.back16,
            contentDescription = null,
            tint = VoyagerTheme.colors.white,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterStart)
                .clickable(onClick = navigateBack),
        )

        Icon(
            imageVector = VoyagerTheme.icons.location24,
            contentDescription = null,
            tint = if (isVisited) VoyagerTheme.colors.font else VoyagerTheme.colors.disabled,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterEnd)
                .clickableUnindicated {
                    addOrRemoveVisitedCounty(!isVisited)
                },
        )
    }
}
