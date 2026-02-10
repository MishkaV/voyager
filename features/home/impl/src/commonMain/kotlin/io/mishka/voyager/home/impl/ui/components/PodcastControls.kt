package io.mishka.voyager.home.impl.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.mishka.voyager.common.audiocontroller.api.models.PlaybackState
import io.mishka.voyager.common.audiocontroller.api.models.PodcastPlaybackInfo
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.theme.icons.next24
import io.mishkav.voyager.core.ui.theme.icons.prev24
import io.mishkav.voyager.core.ui.uikit.utils.clickableUnindicated
import io.mishkav.voyager.core.ui.uikit.utils.toComposeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PodcastControls(
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
