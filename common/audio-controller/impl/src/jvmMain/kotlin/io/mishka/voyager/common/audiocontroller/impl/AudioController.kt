package io.mishka.voyager.common.audiocontroller.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import io.mishka.voyager.common.audiocontroller.api.IAudioController
import io.mishka.voyager.common.audiocontroller.api.models.PlaybackState
import io.mishka.voyager.common.audiocontroller.api.models.PodcastPlaybackInfo
import io.mishkav.voyager.core.utils.context.VoyagerPlatformContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
actual class AudioController actual constructor(
    context: VoyagerPlatformContext,
    audioUrlResolver: io.mishka.voyager.common.audiocontroller.impl.utils.SupabaseAudioUrlResolver
) : IAudioController {

    private val _playbackInfo = MutableStateFlow<PodcastPlaybackInfo?>(null)
    override val playbackInfo: StateFlow<PodcastPlaybackInfo?> = _playbackInfo.asStateFlow()

    private val _playbackState = MutableStateFlow(PlaybackState.IDLE)
    override val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    override suspend fun loadAndPlay(
        podcastId: String,
        audioFullPath: String,
        title: String,
        subtitle: String,
        durationSec: Int
    ) {
        // JVM implementation not supported
    }

    override suspend fun play() {
        // JVM implementation not supported
    }

    override suspend fun pause() {
        // JVM implementation not supported
    }

    override suspend fun stop() {
        _playbackInfo.value = null
        _playbackState.value = PlaybackState.IDLE
    }

    override suspend fun seekTo(positionSec: Int) {
        // JVM implementation not supported
    }

    override suspend fun seekForward(seconds: Int) {
        // JVM implementation not supported
    }

    override suspend fun seekBackward(seconds: Int) {
        // JVM implementation not supported
    }

    override fun getCurrentPosition(): Int = 0

    override fun isPlaying(podcastId: String): Boolean = false
}
