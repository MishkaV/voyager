package io.mishka.voyager.common.audiocontroller.impl

import android.app.Application
import android.content.ComponentName
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import co.touchlab.kermit.Logger
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
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import kotlin.time.Duration.Companion.milliseconds

@Suppress("TooManyFunctions", "MagicNumber")
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
    private var artworkBitmap: Bitmap? = null

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
            loadArtwork()
        }
    }

    private suspend fun loadArtwork() = withContext(Dispatchers.IO) {
        try {
            val res = (context as Application).resources
            val packageName = context.packageName
            val drawableId = res.getIdentifier("background", "drawable", packageName)

            if (drawableId != 0) {
                artworkBitmap = BitmapFactory.decodeResource(res, drawableId)
                Logger.d { "AudioController: Artwork loaded successfully" }
            } else {
                Logger.w { "AudioController: Artwork resource not found" }
            }
        } catch (e: Exception) {
            Logger.e(e) { "AudioController: Failed to load artwork" }
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
    ) = withContext(Dispatchers.Main) {
        Logger.d { "AudioController: loadAndPlay: podcastId=$podcastId, audioFullPath=$audioFullPath" }

        ensureControllerInitialized()
        val controller = mediaController ?: run {
            Logger.e { "AudioController: MediaController not available" }
            updatePlaybackState(PlaybackState.ERROR)
            return@withContext
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

            val metadataBuilder = androidx.media3.common.MediaMetadata.Builder()
                .setTitle(title)
                .setSubtitle(subtitle)
                .setArtist(subtitle)
                .setAlbumTitle("Voyager Podcasts")
                .setMediaType(androidx.media3.common.MediaMetadata.MEDIA_TYPE_PODCAST_EPISODE)

            // Add artwork if available
            artworkBitmap?.let { bitmap ->
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val artworkData = stream.toByteArray()
                metadataBuilder.setArtworkData(
                    artworkData,
                    androidx.media3.common.MediaMetadata.PICTURE_TYPE_FRONT_COVER
                )
            }

            val mediaItem = MediaItem.Builder()
                .setUri(audioUrl)
                .setMediaMetadata(metadataBuilder.build())
                .setMediaId(podcastId)
                .build()

            controller.setMediaItem(mediaItem)
            controller.prepare()
            controller.play()
        } catch (e: Exception) {
            Logger.e(e) { "AudioController: Failed to load audio" }
            updatePlaybackState(PlaybackState.ERROR)
        }
    }

    override suspend fun play() = withContext(Dispatchers.Main) {
        ensureControllerInitialized()
        val controller = mediaController ?: return@withContext
        if (_playbackInfo.value == null) return@withContext

        controller.play()
    }

    override suspend fun pause() = withContext(Dispatchers.Main) {
        ensureControllerInitialized()
        val controller = mediaController ?: return@withContext
        if (_playbackInfo.value == null) return@withContext

        controller.pause()
    }

    override suspend fun stop() = withContext(Dispatchers.Main) {
        ensureControllerInitialized()
        val controller = mediaController ?: return@withContext

        controller.stop()
        controller.clearMediaItems()
        stopPositionUpdateJob()

        currentPodcastId = null
        _playbackInfo.value = null
        _playbackState.value = PlaybackState.IDLE
    }

    override suspend fun seekTo(positionSec: Int) = withContext(Dispatchers.Main) {
        ensureControllerInitialized()
        val controller = mediaController ?: return@withContext
        val info = _playbackInfo.value ?: return@withContext

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
        artworkBitmap?.recycle()
        artworkBitmap = null
    }
}
