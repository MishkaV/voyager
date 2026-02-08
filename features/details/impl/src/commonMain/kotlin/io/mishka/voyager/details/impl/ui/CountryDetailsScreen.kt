package io.mishka.voyager.details.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryWithVisitedStatus
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryAiSuggestEntity
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryBestTimeEntity
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryOverviewEntity
import io.mishka.voyager.details.api.models.CountryDetailsArgs
import io.mishka.voyager.details.impl.ui.blocks.appBarBlock
import io.mishka.voyager.details.impl.ui.blocks.bestTimeBlock
import io.mishka.voyager.details.impl.ui.blocks.generalInfoBlock
import io.mishka.voyager.details.impl.ui.blocks.overviewBlock
import io.mishka.voyager.details.impl.ui.blocks.titleBlock
import io.mishka.voyager.details.impl.ui.utils.toComposeColor
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarDuration
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarMessage
import io.mishkav.voyager.features.navigation.api.snackbar.LocalBottomBottomMainSnackbarController
import io.mishkav.voyager.features.navigation.api.snackbar.MainSnackbarState

@Composable
fun CountryDetailsScreen(
    args: CountryDetailsArgs,
    viewModel: CountryDetailsViewModel,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bottomMainSnackbar = LocalBottomBottomMainSnackbarController.current
    val haptic = LocalHapticFeedback.current

    val countryState = viewModel.countryState.collectAsStateWithLifecycle()
    val aiSuggestsState = viewModel.aiSuggestsState.collectAsStateWithLifecycle()
    val bestTimesState = viewModel.bestTimesState.collectAsStateWithLifecycle()
    val overviewState = viewModel.overviewState.collectAsStateWithLifecycle()

    CountryDetailsScreenContent(
        modifier = modifier,
        args = args,
        countryState = countryState,
        aiSuggestsState = aiSuggestsState,
        bestTimesState = bestTimesState,
        overviewState = overviewState,
        navigateBack = navigateBack,
        addOrRemoveVisitedCounty = { isVisited ->
            if (isVisited) {
                bottomMainSnackbar.show(
                    message = SnackbarMessage(
                        duration = SnackbarDuration.Short,
                        content = MainSnackbarState.Default(
                            text = "Passport stamped. ${args.name} visited!",
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
            viewModel.addOrRemoveVisitedCounty(isVisited)
        }
    )
}

@Composable
private fun CountryDetailsScreenContent(
    args: CountryDetailsArgs,
    countryState: State<UIResult<CountryWithVisitedStatus>>,
    aiSuggestsState: State<UIResult<List<CountryAiSuggestEntity>>>,
    bestTimesState: State<UIResult<List<CountryBestTimeEntity>>>,
    overviewState: State<UIResult<CountryOverviewEntity?>>,
    navigateBack: () -> Unit,
    addOrRemoveVisitedCounty: (isVisited: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .background(args.backgroundHex.toComposeColor())
            .padding(horizontal = 24.dp),
        contentPadding = PaddingValues(bottom = 12.dp)
    ) {
        appBarBlock(
            countryState = countryState,
            navigateBack = navigateBack,
            addOrRemoveVisitedCounty = addOrRemoveVisitedCounty,
        )

        item(contentType = "SPACER") { Spacer(Modifier.height(32.dp)) }

        titleBlock(
            countryName = args.name,
            flagFullPatch = args.flagFullPatch,
        )

        item(contentType = "SPACER") { Spacer(Modifier.height(34.dp)) }

        generalInfoBlock(countryState = countryState)

        item(contentType = "SPACER") { Spacer(Modifier.height(12.dp)) }

        bestTimeBlock(bestTimesState = bestTimesState)

        item(contentType = "SPACER") { Spacer(Modifier.height(12.dp)) }

        overviewBlock(overviewState = overviewState)

        item(contentType = "SPACER") {
            Spacer(Modifier.windowInsetsPadding(WindowInsets.navigationBars))
        }
    }
}
