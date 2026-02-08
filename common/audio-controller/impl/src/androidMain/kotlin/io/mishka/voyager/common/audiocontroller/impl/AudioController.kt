package io.mishka.voyager.common.audiocontroller.impl

import android.app.Application
import android.content.ComponentName
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import io.mishka.voyager.common.audiocontroller.api.IAudioController
import io.mishka.voyager.common.audiocontroller.api.models.PlaybackState
import io.mishka.voyager.common.audiocontroller.api.models.PodcastPlaybackInfo
import io.mishka.voyager.common.audiocontroller.impl.service.PodcastPlaybackService
import io.mishka.voyager.common.audiocontroller.impl.utils.SupabaseAudioUrlResolver
import io.mishkav.voyager.core.utils.context.VoyagerPlatformContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Suppress("TooManyFunctions")
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
actual class AudioController actual constructor(
    private val context: VoyagerPlatformContext,
    private val audioUrlResolver: SupabaseAudioUrlResolver
) : IAudioController {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private var mediaController: MediaController? = null
    private var isControllerInitialized = false

    private val _playbackInfo = MutableStateFlow<PodcastPlaybackInfo?>(null)
    override val playbackInfo: StateFlow<PodcastPlaybackInfo?> = _playbackInfo.asStateFlow()

    private val _playbackState = MutableStateFlow(PlaybackState.IDLE)
    override val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    private var currentPodcastId: String? = null
    private var isPositionUpdateJobActive = false

    private val playerListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                Player.STATE_IDLE -> {
                    updatePlaybackState(PlaybackState.IDLE)
                }
                Player.STATE_BUFFERING -> {
                    updatePlaybackState(PlaybackState.LOADING)
                }
                Player.STATE_READY -> {
                    val controller = mediaController ?: return
                    if (controller.playWhenReady) {
                        updatePlaybackState(PlaybackState.PLAYING)
                        startPositionUpdateJob()
                    } else {
                        updatePlaybackState(PlaybackState.PAUSED)
                        stopPositionUpdateJob()
                    }
                }
                Player.STATE_ENDED -> {
                    updatePlaybackState(PlaybackState.STOPPED)
                    stopPositionUpdateJob()
                }
            }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            val controller = mediaController ?: return
            if (isPlaying) {
                updatePlaybackState(PlaybackState.PLAYING)
                startPositionUpdateJob()
            } else {
                val state = when (controller.playbackState) {
                    Player.STATE_ENDED -> PlaybackState.STOPPED
                    Player.STATE_IDLE -> PlaybackState.IDLE
                    else -> PlaybackState.PAUSED
                }
                updatePlaybackState(state)
                stopPositionUpdateJob()
            }
        }

        override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
            Logger.e(error) { "AudioController: Player error" }
            updatePlaybackState(PlaybackState.ERROR)
            stopPositionUpdateJob()
        }
    }

    init {
        scope.launch {
            initializeController()
        }
    }

    private suspend fun initializeController() {
        try {
            val sessionToken = SessionToken(
                context as Application,
                ComponentName(context, PodcastPlaybackService::class.java)
            )

            val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
            mediaController = controllerFuture.await().also { controller ->
                controller.addListener(playerListener)
                isControllerInitialized = true
                Logger.d { "AudioController: MediaController initialized" }
            }
        } catch (e: Exception) {
            Logger.e(e) { "AudioController: Failed to initialize MediaController" }
        }
    }

    private suspend fun ensureControllerInitialized() {
        if (!isControllerInitialized) {
            initializeController()
        }
    }

    override suspend fun loadAndPlay(
        podcastId: String,
        audioFullPath: String,
        title: String,
        subtitle: String,
        durationSec: Int
    ) {
        Logger.d { "AudioController: loadAndPlay: podcastId=$podcastId, audioFullPath=$audioFullPath" }

        ensureControllerInitialized()
        val controller = mediaController ?: run {
            Logger.e { "AudioController: MediaController not available" }
            updatePlaybackState(PlaybackState.ERROR)
            return
        }

        stop()

        currentPodcastId = podcastId
        _playbackState.value = PlaybackState.LOADING

        _playbackInfo.value = PodcastPlaybackInfo(
            podcastId = podcastId,
            audioFullPath = audioFullPath,
            title = title,
            subtitle = subtitle,
            durationSec = durationSec,
            currentPositionSec = 0,
            playbackState = PlaybackState.LOADING
        )

        try {
            // Resolve audioFullPath to signed URL
            val audioUrl = audioUrlResolver.resolveUrl(audioFullPath)
            Logger.d { "AudioController: Resolved audio URL successfully" }

            val mediaItem = MediaItem.Builder()
                .setUri(audioUrl)
                .setMediaMetadata(
                    androidx.media3.common.MediaMetadata.Builder()
                        .setTitle(title)
                        .setSubtitle(subtitle)
                        .build()
                )
                .build()

            controller.setMediaItem(mediaItem)
            controller.prepare()
            controller.play()
        } catch (e: Exception) {
            Logger.e(e) { "AudioController: Failed to load audio" }
            updatePlaybackState(PlaybackState.ERROR)
        }
    }

    override suspend fun play() {
        ensureControllerInitialized()
        val controller = mediaController ?: return
        if (_playbackInfo.value == null) return

        controller.play()
    }

    override suspend fun pause() {
        ensureControllerInitialized()
        val controller = mediaController ?: return
        if (_playbackInfo.value == null) return

        controller.pause()
    }

    override suspend fun stop() {
        ensureControllerInitialized()
        val controller = mediaController ?: return

        controller.stop()
        controller.clearMediaItems()
        stopPositionUpdateJob()

        currentPodcastId = null
        _playbackInfo.value = null
        _playbackState.value = PlaybackState.IDLE
    }

    override suspend fun seekTo(positionSec: Int) {
        ensureControllerInitialized()
        val controller = mediaController ?: return
        val info = _playbackInfo.value ?: return

        val positionMs = (positionSec * 1000L).coerceIn(0, info.durationSec * 1000L)
        controller.seekTo(positionMs)
        updatePlaybackInfoWithPosition()
    }

    override suspend fun seekForward(seconds: Int) {
        val currentPos = getCurrentPosition()
        seekTo(currentPos + seconds)
    }

    override suspend fun seekBackward(seconds: Int) {
        val currentPos = getCurrentPosition()
        seekTo((currentPos - seconds).coerceAtLeast(0))
    }

    override fun getCurrentPosition(): Int {
        val controller = mediaController ?: return 0
        val millsInSec = 1000
        return (controller.currentPosition / millsInSec).toInt()
    }

    override fun isPlaying(podcastId: String): Boolean {
        val controller = mediaController ?: return false
        return currentPodcastId == podcastId && controller.isPlaying
    }

    private fun updatePlaybackState(state: PlaybackState) {
        _playbackState.value = state
        updatePlaybackInfoWithPosition()
    }

    private fun updatePlaybackInfoWithPosition() {
        val current = _playbackInfo.value ?: return
        _playbackInfo.value = current.copy(
            currentPositionSec = getCurrentPosition(),
            playbackState = _playbackState.value
        )
    }

    private fun startPositionUpdateJob() {
        if (isPositionUpdateJobActive) return
        isPositionUpdateJobActive = true

        scope.launch {
            while (isActive && isPositionUpdateJobActive) {
                updatePlaybackInfoWithPosition()
                delay(500.milliseconds)
            }
        }
    }

    private fun stopPositionUpdateJob() {
        isPositionUpdateJobActive = false
    }

    fun release() {
        stopPositionUpdateJob()
        scope.cancel()
        mediaController?.removeListener(playerListener)
        mediaController?.release()
        mediaController = null
        isControllerInitialized = false
    }
}
