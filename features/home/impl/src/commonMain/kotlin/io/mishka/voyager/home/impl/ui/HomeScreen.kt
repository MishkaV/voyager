package io.mishka.voyager.home.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.mishka.voyager.features.main.api.consts.MAIN_BOTTOM_BAR_HEIGHT
import io.mishka.voyager.home.impl.ui.components.CountrySnackbar
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.snackbar.compose.noOverlapBottomContentBySnackbar
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarDuration
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarMessage
import io.mishkav.voyager.features.navigation.api.LocalRootNavigation
import io.mishkav.voyager.features.navigation.api.model.RootConfig
import io.mishkav.voyager.features.navigation.api.snackbar.LocalBottomMainSnackbarController
import io.mishkav.voyager.features.navigation.api.snackbar.MainSnackbarState
import org.jetbrains.compose.resources.stringResource
import voyager.features.home.impl.generated.resources.Res
import voyager.features.home.impl.generated.resources.home_title

@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {
    HomeScreenContent(
        viewModel = viewModel,
        modifier = modifier,
    )
}

@Composable
private fun HomeScreenContent(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {
    val rootNavigation = LocalRootNavigation.current
    val haptic = LocalHapticFeedback.current
    val bottomMainSnackbar = LocalBottomMainSnackbarController.current

    val selectedCountry = viewModel.selectedCountry.collectAsStateWithLifecycle()
    val playbackState = viewModel.playbackState.collectAsStateWithLifecycle()
    val playbackInfo = viewModel.playbackInfo.collectAsStateWithLifecycle()
    val podcastInfoState = viewModel.podcastInfoState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier.background(VoyagerTheme.colors.background),
    ) {
        MapboxMapView(
            viewModel = viewModel,
            modifier = Modifier.fillMaxSize(),
        )

        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(top = 16.dp)
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            text = stringResource(Res.string.home_title),
            style = VoyagerTheme.typography.h1,
            textAlign = TextAlign.Start,
            color = VoyagerTheme.colors.font
        )

        CountrySnackbar(
            selectedCountry = selectedCountry,
            podcastInfoState = podcastInfoState,
            playbackInfoState = playbackInfo,
            playbackState = playbackState,
            openCountryDetails = { country ->
                rootNavigation.push(
                    config = RootConfig.CountryDetails(
                        countryId = country.country.id,
                        name = country.country.name,
                        flagFullPatch = country.country.flagFullPatch,
                        backgroundHex = country.country.backgroundHex,
                    )
                )
            },
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
            playPodcast = viewModel::playPodcast,
            pausePodcast = viewModel::pausePodcast,
            seekTo = viewModel::seekTo,
            seekForward = viewModel::seekForward,
            seekBackward = viewModel::seekBackward,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .noOverlapBottomContentBySnackbar()
                .padding(horizontal = 24.dp)
                .padding(bottom = MAIN_BOTTOM_BAR_HEIGHT + 12.dp)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .fillMaxWidth(),
        )
    }
}

@Composable
internal expect fun MapboxMapView(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
)
