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
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import io.mishka.voyager.core.repositories.countries.api.models.local.Continent
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryWithVisitedStatus
import io.mishka.voyager.features.main.api.consts.MAIN_BOTTOM_BAR_HEIGHT
import io.mishka.voyager.search.impl.ui.blocks.countriesListBlock
import io.mishka.voyager.search.impl.ui.components.SearchAppBar
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
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
    val continentState = viewModel.selectedContinent.collectAsStateWithLifecycle()
    val countriesState = viewModel.countriesState.collectAsLazyPagingItems()

    val searchState = rememberTextFieldState()

    LaunchedEffect(Unit) {
        snapshotFlow { searchState.text }.collect { newText ->
            viewModel.updateQuery(newText.toString())
        }
    }

    SearchScreenContent(
        continentState = continentState,
        countriesState = countriesState,
        searchState = searchState,
        navigateToGeneralSearch = {
            viewModel.selectContinent(null)
        },
        modifier = modifier,
    )
}

@Composable
private fun SearchScreenContent(
    continentState: State<Continent?>,
    countriesState: LazyPagingItems<CountryWithVisitedStatus>,
    searchState: TextFieldState,
    navigateToGeneralSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isSearchActive = remember {
        derivedStateOf { searchState.text.isNotEmpty() }
    }
    val isAppBarVisible = remember {
        derivedStateOf { continentState.value != null }
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
            onBack = navigateToGeneralSearch
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
            contentPadding = PaddingValues(
                top = 36.dp,
                bottom = MAIN_BOTTOM_BAR_HEIGHT + 12.dp,
            ),
        ) {
            countriesListBlock(
                countriesState = countriesState,
            )
        }
    }
}
