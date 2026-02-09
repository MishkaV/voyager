package io.mishka.voyager.details.impl.ui.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.mishka.voyager.common.audiocontroller.api.models.PlaybackState
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.theme.icons.pause24
import io.mishkav.voyager.core.ui.theme.icons.play24
import io.mishkav.voyager.core.ui.uikit.utils.clickableUnindicated

@Composable
internal fun PlayButton(
    playbackState: State<PlaybackState>,
    onClick: (PlaybackState) -> Unit,
    modifier: Modifier = Modifier,
) {
    val enabled = remember {
        derivedStateOf {
            playbackState.value != PlaybackState.ERROR && playbackState.value != PlaybackState.LOADING
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(VoyagerTheme.colors.white.copy(alpha = 0.8f))
            .clickableUnindicated(enabled = enabled.value) {
                onClick(playbackState.value)
            }
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
    ) {
        when (playbackState.value) {
            PlaybackState.IDLE,
            PlaybackState.PLAYING,
            PlaybackState.PAUSED,
            PlaybackState.STOPPED -> {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = when (playbackState.value) {
                        PlaybackState.IDLE -> VoyagerTheme.icons.play24
                        PlaybackState.PLAYING -> VoyagerTheme.icons.pause24
                        PlaybackState.PAUSED -> VoyagerTheme.icons.play24
                        PlaybackState.STOPPED -> VoyagerTheme.icons.play24
                        else -> VoyagerTheme.icons.play24
                    },
                    contentDescription = null,
                    tint = VoyagerTheme.colors.black,
                )
            }

            PlaybackState.LOADING, PlaybackState.ERROR -> CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = VoyagerTheme.colors.black,
            )
        }
    }
}
