package io.mishka.voyager.common.audiocontroller.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import io.mishka.voyager.common.audiocontroller.api.IAudioController
import io.mishka.voyager.common.audiocontroller.api.models.PlaybackState
import io.mishka.voyager.common.audiocontroller.api.models.PodcastPlaybackInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class AudioController : IAudioController {

    private val _playbackInfo = MutableStateFlow<PodcastPlaybackInfo?>(null)
    override val playbackInfo: StateFlow<PodcastPlaybackInfo?> = _playbackInfo.asStateFlow()

    private val _playbackState = MutableStateFlow(PlaybackState.IDLE)
    override val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    private var currentPosition: Int = 0

    override suspend fun loadAndPlay(
        podcastId: String,
        audioUrl: String,
        title: String,
        subtitle: String,
        durationSec: Int
    ) {
        // Stop current playback if any
        stop()

        _playbackState.value = PlaybackState.LOADING

        // Create new playback info
        _playbackInfo.value = PodcastPlaybackInfo(
            podcastId = podcastId,
            audioUrl = audioUrl,
            title = title,
            subtitle = subtitle,
            durationSec = durationSec,
            currentPositionSec = 0,
            playbackState = PlaybackState.LOADING
        )

        // TODO: Platform-specific implementation to load and play audio
        // For now, simulate loading
        _playbackState.value = PlaybackState.PLAYING
        currentPosition = 0
        updatePlaybackInfo()
    }

    override suspend fun play() {
        if (_playbackInfo.value == null) return

        _playbackState.value = PlaybackState.PLAYING
        updatePlaybackInfo()

        // TODO: Platform-specific implementation to resume playback
    }

    override suspend fun pause() {
        if (_playbackInfo.value == null) return

        _playbackState.value = PlaybackState.PAUSED
        updatePlaybackInfo()

        // TODO: Platform-specific implementation to pause playback
    }

    override suspend fun stop() {
        _playbackState.value = PlaybackState.STOPPED
        currentPosition = 0

        // TODO: Platform-specific implementation to stop playback

        _playbackInfo.value = null
        _playbackState.value = PlaybackState.IDLE
    }

    override suspend fun seekTo(positionSec: Int) {
        val info = _playbackInfo.value ?: return

        currentPosition = positionSec.coerceIn(0, info.durationSec)
        updatePlaybackInfo()

        // TODO: Platform-specific implementation to seek
    }

    override suspend fun seekForward(seconds: Int) {
        val info = _playbackInfo.value ?: return
        seekTo(currentPosition + seconds)
    }

    override suspend fun seekBackward(seconds: Int) {
        val info = _playbackInfo.value ?: return
        seekTo((currentPosition - seconds).coerceAtLeast(0))
    }

    override fun getCurrentPosition(): Int = currentPosition

    override fun isPlaying(podcastId: String): Boolean {
        return _playbackInfo.value?.podcastId == podcastId &&
            _playbackState.value == PlaybackState.PLAYING
    }

    private fun updatePlaybackInfo() {
        val current = _playbackInfo.value ?: return
        _playbackInfo.value = current.copy(
            currentPositionSec = currentPosition,
            playbackState = _playbackState.value
        )
    }
}
