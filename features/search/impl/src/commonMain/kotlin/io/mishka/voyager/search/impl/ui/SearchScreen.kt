package io.mishka.voyager.search.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mishka.voyager.features.main.api.consts.MAIN_BOTTOM_BAR_HEIGHT
import io.mishka.voyager.search.impl.ui.components.SearchAppBar
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.appbar.SimpleVoyagerAppBar
import io.mishkav.voyager.core.ui.uikit.textfield.VoyagerTextField
import org.jetbrains.compose.resources.stringResource
import voyager.features.search.impl.generated.resources.Res
import voyager.features.search.impl.generated.resources.search_tab_title
import voyager.features.search.impl.generated.resources.search_textfield_placeholder

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    modifier: Modifier = Modifier,
) {

    SearchScreenContent(
        modifier = modifier,
    )
}


@Composable
private fun SearchScreenContent(
    modifier: Modifier = Modifier,
) {
    val searchState = rememberTextFieldState()

    // TODO
    val isAppBarVisible = remember {
        derivedStateOf { searchState.text.isNotEmpty() }
    }

    Column(
        modifier = modifier
            .background(VoyagerTheme.colors.background)
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(top = 16.dp)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchAppBar(
            isVisible = isAppBarVisible.value,
            onBack = {
                // TODO
            }
        )

        // TODO Add change text if selected continent
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.search_tab_title),
            style = VoyagerTheme.typography.h1,
            textAlign = TextAlign.Start,
            color = VoyagerTheme.colors.font
        )

        Spacer(Modifier.height(16.dp))

        VoyagerTextField(
            modifier = Modifier.fillMaxWidth(),
            state = searchState,
            label = stringResource(Res.string.search_textfield_placeholder)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = MAIN_BOTTOM_BAR_HEIGHT + 12.dp)
        ) {
            item { Spacer(Modifier.height(36.dp)) }


        }
    }
}
