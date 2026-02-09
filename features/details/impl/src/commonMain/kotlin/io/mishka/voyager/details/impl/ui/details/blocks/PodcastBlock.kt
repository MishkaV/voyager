package io.mishka.voyager.details.impl.ui.details.blocks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
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
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryPodcastEntity
import io.mishka.voyager.details.impl.ui.details.components.PlayButton
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import io.mishkav.voyager.core.ui.uikit.resultflow.isLoading
import io.mishkav.voyager.core.ui.uikit.resultflow.successOrNull
import io.mishkav.voyager.core.ui.uikit.shimmer.placeholderFadeConnecting
import org.jetbrains.compose.resources.stringResource
import voyager.features.details.impl.generated.resources.Res
import voyager.features.details.impl.generated.resources.details_podcast

internal fun LazyListScope.podcastBlock(
    podcastInfoState: State<UIResult<CountryPodcastEntity?>>,
    playbackState: State<PlaybackState>,
    playPodcast: (CountryPodcastEntity) -> Unit,
    pausePodcast: () -> Unit,
) {
    item(
        key = "PODCAST_BLOCK_KEY",
        contentType = "PODCAST_BLOCK_TYPE",
    ) {
        PodcastBlock(
            podcastInfoState = podcastInfoState,
            playbackState = playbackState,
            playPodcast = playPodcast,
            pausePodcast = pausePodcast,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun PodcastBlock(
    podcastInfoState: State<UIResult<CountryPodcastEntity?>>,
    playbackState: State<PlaybackState>,
    playPodcast: (CountryPodcastEntity) -> Unit,
    pausePodcast: () -> Unit,
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

        // TODO Add controlls
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
