package io.mishka.voyager.details.impl.ui.details.blocks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mishka.voyager.common.audiocontroller.api.models.PlaybackState
import io.mishka.voyager.common.audiocontroller.api.models.PodcastPlaybackInfo
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryPodcastEntity
import io.mishka.voyager.details.impl.ui.details.components.PlayButton
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.theme.icons.next24
import io.mishkav.voyager.core.ui.theme.icons.prev24
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import io.mishkav.voyager.core.ui.uikit.resultflow.isLoading
import io.mishkav.voyager.core.ui.uikit.resultflow.isSuccess
import io.mishkav.voyager.core.ui.uikit.resultflow.successOrNull
import io.mishkav.voyager.core.ui.uikit.shimmer.placeholderFadeConnecting
import io.mishkav.voyager.core.ui.uikit.utils.clickableUnindicated
import io.mishkav.voyager.core.ui.uikit.utils.toComposeColor
import org.jetbrains.compose.resources.stringResource
import voyager.features.details.impl.generated.resources.Res
import voyager.features.details.impl.generated.resources.details_podcast

internal fun LazyListScope.podcastBlock(
    backgroundHex: String,
    podcastInfoState: State<UIResult<CountryPodcastEntity?>>,
    playbackState: State<PlaybackState>,
    playbackInfo: State<PodcastPlaybackInfo?>,
    playPodcast: (CountryPodcastEntity) -> Unit,
    pausePodcast: () -> Unit,
    seekTo: (Int) -> Unit,
    seekForward: () -> Unit,
    seekBackward: () -> Unit,
) {
    item(
        key = "PODCAST_BLOCK_KEY",
        contentType = "PODCAST_BLOCK_TYPE",
    ) {
        AnimatedVisibility(
            visible = podcastInfoState.value.isSuccess(),
            enter = expandVertically() + slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut(),
        ) {
            PodcastBlock(
                backgroundHex = backgroundHex,
                podcastInfoState = podcastInfoState,
                playbackState = playbackState,
                playbackInfo = playbackInfo,
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
private fun PodcastBlock(
    backgroundHex: String,
    podcastInfoState: State<UIResult<CountryPodcastEntity?>>,
    playbackState: State<PlaybackState>,
    playbackInfo: State<PodcastPlaybackInfo?>,
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
            .background(VoyagerTheme.colors.white.copy(alpha = 0.2f))
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(Res.string.details_podcast),
            style = VoyagerTheme.typography.h3,
            color = VoyagerTheme.colors.white,
            textAlign = TextAlign.Start,
        )

        Spacer(Modifier.height(16.dp))

        PodcastInfo(
            podcastInfoState = podcastInfoState,
            playbackState = playbackState,
            playPodcast = playPodcast,
            pausePodcast = pausePodcast,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(16.dp))

        PodcastControls(
            backgroundHex = backgroundHex,
            playbackInfo = playbackInfo,
            playbackState = playbackState,
            seekTo = seekTo,
            seekForward = seekForward,
            seekBackward = seekBackward,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun PodcastInfo(
    podcastInfoState: State<UIResult<CountryPodcastEntity?>>,
    playbackState: State<PlaybackState>,
    playPodcast: (CountryPodcastEntity) -> Unit,
    pausePodcast: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isLoading = remember {
        derivedStateOf { podcastInfoState.value.isLoading() }
    }
    val sharedPlaceholderModifier = Modifier.placeholderFadeConnecting(
        shape = RoundedCornerShape(16.dp),
        visible = isLoading.value
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(sharedPlaceholderModifier),
                text = podcastInfoState.value.successOrNull()?.title.orEmpty(),
                style = VoyagerTheme.typography.h3,
                color = VoyagerTheme.colors.white,
                textAlign = TextAlign.Start,
            )

            Spacer(Modifier.height(2.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(sharedPlaceholderModifier),
                text = podcastInfoState.value.successOrNull()?.subtitle.orEmpty(),
                style = VoyagerTheme.typography.caption,
                color = VoyagerTheme.colors.white,
                textAlign = TextAlign.Start,
            )
        }

        Spacer(Modifier.width(20.dp))

        PlayButton(
            modifier = Modifier.placeholderFadeConnecting(
                shape = RoundedCornerShape(24.dp),
                visible = isLoading.value
            ),
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PodcastControls(
    backgroundHex: String,
    playbackInfo: State<PodcastPlaybackInfo?>,
    playbackState: State<PlaybackState>,
    seekTo: (Int) -> Unit,
    seekForward: () -> Unit,
    seekBackward: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val progress = remember(playbackInfo.value) {
        derivedStateOf {
            playbackInfo.value?.let { info ->
                info.currentPositionSec.toFloat() / info.durationSec
            } ?: 0f
        }
    }
    val amplitude = remember(playbackState.value) {
        derivedStateOf {
            when (playbackState.value) {
                PlaybackState.IDLE,
                PlaybackState.PAUSED,
                PlaybackState.STOPPED,
                PlaybackState.ERROR,
                PlaybackState.LOADING -> 0f

                PlaybackState.PLAYING -> 1f
            }
        }
    }
    val isEnabled = remember(playbackState.value) {
        derivedStateOf {
            when (playbackState.value) {
                PlaybackState.IDLE,
                PlaybackState.STOPPED,
                PlaybackState.ERROR -> false

                PlaybackState.PAUSED,
                PlaybackState.LOADING,
                PlaybackState.PLAYING -> true
            }
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(24.dp).clickableUnindicated(
                enabled = isEnabled.value,
                onClick = seekBackward,
            ),
            imageVector = VoyagerTheme.icons.prev24,
            contentDescription = null,
            tint = VoyagerTheme.colors.white,
        )

        Spacer(Modifier.width(8.dp))

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            LinearWavyProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = { progress.value },
                amplitude = { amplitude.value },
                color = backgroundHex.toComposeColor().copy(alpha = 1f),
                trackColor = VoyagerTheme.colors.white.copy(alpha = 0.8f),
            )

            Slider(
                modifier = Modifier.fillMaxWidth().height(16.dp),
                value = progress.value,
                enabled = isEnabled.value,
                onValueChange = { currentProgress ->
                    val totalSec = playbackInfo.value?.durationSec ?: 0
                    val secToSeek = (totalSec * currentProgress).toInt()
                    seekTo(secToSeek)
                },
                colors = SliderDefaults.colors(
                    thumbColor = VoyagerTheme.colors.white,
                    activeTrackColor = androidx.compose.ui.graphics.Color.Transparent,
                    inactiveTrackColor = androidx.compose.ui.graphics.Color.Transparent,
                    disabledThumbColor = VoyagerTheme.colors.white,
                    disabledActiveTrackColor = androidx.compose.ui.graphics.Color.Transparent,
                    disabledInactiveTrackColor = androidx.compose.ui.graphics.Color.Transparent,
                )
            )
        }

        Spacer(Modifier.width(8.dp))

        Icon(
            modifier = Modifier.size(24.dp).clickableUnindicated(
                enabled = isEnabled.value,
                onClick = seekForward
            ),
            imageVector = VoyagerTheme.icons.next24,
            contentDescription = null,
            tint = VoyagerTheme.colors.white,
        )
    }
}
