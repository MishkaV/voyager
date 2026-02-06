package io.mishka.voyager.search.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.arkivanov.essenty.backhandler.BackHandler
import io.mishka.voyager.core.repositories.countries.api.models.local.Continent
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryWithVisitedStatus
import io.mishka.voyager.features.main.api.consts.MAIN_BOTTOM_BAR_HEIGHT
import io.mishka.voyager.features.main.api.snackbar.LocalBottomBottomMainSnackbarController
import io.mishka.voyager.features.main.api.snackbar.MainSnackbarState
import io.mishka.voyager.search.impl.ui.blocks.TitleBlock
import io.mishka.voyager.search.impl.ui.blocks.continentsBlock
import io.mishka.voyager.search.impl.ui.blocks.countriesListBlock
import io.mishka.voyager.search.impl.ui.components.SearchAppBar
import io.mishka.voyager.search.impl.ui.utils.toRootCountryConfig
import io.mishkav.voyager.core.ui.decompose.BackHandler
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarDuration
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarMessage
import io.mishkav.voyager.core.ui.uikit.textfield.VoyagerTextField
import io.mishkav.voyager.features.navigation.api.LocalRootNavigation
import org.jetbrains.compose.resources.stringResource
import voyager.features.search.impl.generated.resources.Res
import voyager.features.search.impl.generated.resources.search_textfield_placeholder

@Composable
fun SearchScreen(
    backHandler: BackHandler,
    viewModel: SearchViewModel,
    modifier: Modifier = Modifier,
) {
    val haptic = LocalHapticFeedback.current
    val bottomMainSnackbar = LocalBottomBottomMainSnackbarController.current

    val continentState = viewModel.selectedContinent.collectAsStateWithLifecycle()
    val countriesState = viewModel.countriesState.collectAsLazyPagingItems()

    val searchState = rememberTextFieldState()

    BackHandler(
        backHandler = backHandler,
        isEnabled = continentState.value != null,
    ) {
        viewModel.selectContinent(null)
    }

    LaunchedEffect(Unit) {
        snapshotFlow { searchState.text }.collect { newText ->
            viewModel.updateQuery(newText.toString())
        }
    }

    SearchScreenContent(
        continentState = continentState,
        countriesState = countriesState,
        searchState = searchState,
        addOrRemoveVisitedCounty = { country ->
            if (country.isVisited) {
                bottomMainSnackbar.show(
                    message = SnackbarMessage(
                        duration = SnackbarDuration.Short,
                        content = MainSnackbarState.Default(
                            text = "Passport stamped. ${country.country.name} visited!",
                            buttonText = "Superb!",
                        ),
                    )
                )
                haptic.performHapticFeedback(HapticFeedbackType.ToggleOn)
            } else {
                bottomMainSnackbar.show(
                    message = SnackbarMessage(
                        duration = SnackbarDuration.Short,
                        content = MainSnackbarState.Default(
                            text = "Visit undone. Adventure pending!",
                            buttonText = "Good",
                        ),
                    )
                )
                haptic.performHapticFeedback(HapticFeedbackType.ToggleOff)
            }
            viewModel.addOrRemoveVisitedCounty(
                countryId = country.country.id,
                isVisited = country.isVisited,
            )
        },
        navigateToGeneralSearch = {
            viewModel.selectContinent(null)
        },
        selectContinent = viewModel::selectContinent,
        modifier = modifier,
    )
}

@Composable
private fun SearchScreenContent(
    continentState: State<Continent?>,
    countriesState: LazyPagingItems<CountryWithVisitedStatus>,
    searchState: TextFieldState,
    addOrRemoveVisitedCounty: (CountryWithVisitedStatus) -> Unit,
    navigateToGeneralSearch: () -> Unit,
    selectContinent: (Continent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val rootNavigation = LocalRootNavigation.current
    val density = LocalDensity.current
    val focusManager = LocalFocusManager.current

    val focusRequester = remember { FocusRequester() }
    val isTextFieldInFocus = remember { mutableStateOf(false) }
    val isSearchActive = remember {
        derivedStateOf { searchState.text.isNotEmpty() }
    }
    val isAppBarVisible = remember {
        derivedStateOf { continentState.value != null }
    }
    val isContinentContentVisible = remember {
        derivedStateOf {
            !isSearchActive.value && continentState.value == null && !isTextFieldInFocus.value
        }
    }

    val imeBottom = WindowInsets.ime.getBottom(density)
    val isImeVisible = remember(imeBottom) { derivedStateOf { imeBottom > 0 } }
    LaunchedEffect(isImeVisible.value) {
        if (!isImeVisible.value && isTextFieldInFocus.value) {
            focusManager.clearFocus()
        }
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

        TitleBlock(
            continentState = continentState,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(16.dp))

        VoyagerTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { state ->
                    isTextFieldInFocus.value = state.isFocused
                },
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
            continentsBlock(
                isContinentContentVisible = isContinentContentVisible,
                selectContinent = selectContinent,
            )

            countriesListBlock(
                addOrRemoveVisitedCounty = addOrRemoveVisitedCounty,
                navigateToCountryInfo = { country ->
                    rootNavigation.push(
                        config = country.country.toRootCountryConfig()
                    )
                },
                countriesState = countriesState,
            )
        }
    }
}
