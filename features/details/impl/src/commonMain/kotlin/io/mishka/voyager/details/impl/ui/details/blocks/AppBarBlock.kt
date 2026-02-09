package io.mishka.voyager.details.impl.ui.details.blocks

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
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryWithVisitedStatus
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.theme.icons.back16
import io.mishkav.voyager.core.ui.theme.icons.location24
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import io.mishkav.voyager.core.ui.uikit.utils.clickableUnindicated

internal fun LazyListScope.appBarBlock(
    countryState: State<UIResult<CountryWithVisitedStatus>>,
    navigateBack: () -> Unit,
    addOrRemoveVisitedCounty: (isVisited: Boolean) -> Unit,
) {
    item(
        key = "APP_BAR_KEY",
        contentType = "APP_BAR_CONTENT_TYPE"
    ) {
        val isVisited = remember(countryState.value) {
            derivedStateOf {
                (countryState.value as? UIResult.Success)?.data?.isVisited ?: false
            }
        }
        val isLocationChangeActive = remember(countryState.value) {
            derivedStateOf {
                countryState.value is UIResult.Success
            }
        }

        AppBarBlock(
            isVisited = isVisited.value,
            isLocationChangeActive = isLocationChangeActive.value,
            navigateBack = navigateBack,
            addOrRemoveVisitedCounty = addOrRemoveVisitedCounty,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun AppBarBlock(
    isVisited: Boolean,
    isLocationChangeActive: Boolean,
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
                .clickableUnindicated(enabled = isLocationChangeActive) {
                    addOrRemoveVisitedCounty(!isVisited)
                },
        )
    }
}
