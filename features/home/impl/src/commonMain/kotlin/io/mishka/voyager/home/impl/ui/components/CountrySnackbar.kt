package io.mishka.voyager.home.impl.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mishka.voyager.common.audiocontroller.api.models.PlaybackState
import io.mishka.voyager.common.audiocontroller.api.models.PodcastPlaybackInfo
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryPodcastEntity
import io.mishka.voyager.home.impl.ui.models.CountryUIModel
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.theme.icons.location24
import io.mishkav.voyager.core.ui.uikit.image.VoyagerSharedImage
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import io.mishkav.voyager.core.ui.uikit.resultflow.successOrNull
import io.mishkav.voyager.core.ui.uikit.transition.LocalSharedTransitionScope
import io.mishkav.voyager.core.ui.uikit.utils.clickableUnindicated
import io.mishkav.voyager.core.ui.uikit.utils.toComposeColor
import io.mishkav.voyager.core.utils.supabase.voyagerAuthenticatedStorageItem

private const val ANIMATION_DURATION = 300

@Composable
internal fun CountrySnackbar(
    selectedCountry: State<CountryUIModel?>,
    podcastInfoState: State<UIResult<CountryPodcastEntity?>>,
    playbackInfoState: State<PodcastPlaybackInfo?>,
    playbackState: State<PlaybackState>,
    openCountryDetails: (CountryUIModel) -> Unit,
    addOrRemoveVisitedCounty: (CountryUIModel) -> Unit,
    playPodcast: (CountryPodcastEntity) -> Unit,
    pausePodcast: () -> Unit,
    seekTo: (Int) -> Unit,
    seekForward: () -> Unit,
    seekBackward: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = selectedCountry.value,
        contentKey = { it?.country?.id },
        transitionSpec = {
            slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = spring()
            ) + fadeIn(
                animationSpec = tween(ANIMATION_DURATION)
            ) togetherWith slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeOut(
                animationSpec = tween(ANIMATION_DURATION)
            )
        }
    ) { country ->
        country?.let { country ->
            CountrySnackbarContent(
                country = country,
                podcastInfoState = podcastInfoState,
                playbackInfoState = playbackInfoState,
                playbackState = playbackState,
                addOrRemoveVisitedCounty = addOrRemoveVisitedCounty,
                openCountryDetails = openCountryDetails,
                playPodcast = playPodcast,
                pausePodcast = pausePodcast,
                seekTo = seekTo,
                seekForward = seekForward,
                seekBackward = seekBackward,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun CountrySnackbarContent(
    country: CountryUIModel,
    podcastInfoState: State<UIResult<CountryPodcastEntity?>>,
    playbackInfoState: State<PodcastPlaybackInfo?>,
    playbackState: State<PlaybackState>,
    addOrRemoveVisitedCounty: (CountryUIModel) -> Unit,
    openCountryDetails: (CountryUIModel) -> Unit,
    playPodcast: (CountryPodcastEntity) -> Unit,
    pausePodcast: () -> Unit,
    seekTo: (Int) -> Unit,
    seekForward: () -> Unit,
    seekBackward: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(country.country.backgroundHex.toComposeColor())
            .clickableUnindicated {
                openCountryDetails(country)
            }
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
    ) {
        CountryInfo(
            country = country,
            podcastInfoState = podcastInfoState,
            playbackInfoState = playbackInfoState,
            playbackState = playbackState,
            addOrRemoveVisitedCounty = addOrRemoveVisitedCounty,
            playPodcast = playPodcast,
            pausePodcast = pausePodcast,
            modifier = Modifier.fillMaxWidth(),
        )

        if (playbackInfoState.value != null) {
            Spacer(Modifier.height(12.dp))

            PodcastControls(
                backgroundHex = country.country.backgroundHex,
                playbackInfo = playbackInfoState,
                playbackState = playbackState,
                seekTo = seekTo,
                seekForward = seekForward,
                seekBackward = seekBackward
            )
        }
    }
}

@Composable
private fun CountryInfo(
    country: CountryUIModel,
    podcastInfoState: State<UIResult<CountryPodcastEntity?>>,
    playbackInfoState: State<PodcastPlaybackInfo?>,
    playbackState: State<PlaybackState>,
    addOrRemoveVisitedCounty: (CountryUIModel) -> Unit,
    playPodcast: (CountryPodcastEntity) -> Unit,
    pausePodcast: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sharedScope = LocalSharedTransitionScope.current
    val countVibesToShow = 3

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        with(sharedScope) {
            VoyagerSharedImage(
                modifier = Modifier.size(32.dp),
                data = voyagerAuthenticatedStorageItem(country.country.flagFullPatch),
                contentDescription = null,
                shareKey = "country_${country.country.name}",
                shape = CircleShape,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = country.country.name,
                style = VoyagerTheme.typography.h3,
                color = VoyagerTheme.colors.font,
                textAlign = TextAlign.Start,
            )

            Spacer(Modifier.height(6.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = country.vibes.take(countVibesToShow).joinToString(
                    separator = " · "
                ) { it.title },
                style = VoyagerTheme.typography.caption,
                color = VoyagerTheme.colors.white,
                textAlign = TextAlign.Start,
            )
        }

        Spacer(Modifier.width(16.dp))

        if (playbackInfoState.value != null) {
            PlayButton(
                playbackState = playbackState,
                onClick = { state ->
                    podcastInfoState.value.successOrNull()?.let { info ->
                        when (state) {
                            PlaybackState.IDLE,
                            PlaybackState.PAUSED,
                            PlaybackState.STOPPED -> playPodcast(info)

                            PlaybackState.PLAYING -> pausePodcast()

                            PlaybackState.LOADING,
                            PlaybackState.ERROR -> Unit
                        }
                    }
                },
            )
        } else {
            Icon(
                modifier = Modifier.clickableUnindicated {
                    addOrRemoveVisitedCounty(country.copy(isVisited = !country.isVisited))
                },
                imageVector = VoyagerTheme.icons.location24,
                contentDescription = null,
                tint = if (country.isVisited) VoyagerTheme.colors.font else VoyagerTheme.colors.disabled,
            )
        }
    }
}
