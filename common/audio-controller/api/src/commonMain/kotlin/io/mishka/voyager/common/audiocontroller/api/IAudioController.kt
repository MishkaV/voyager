package io.mishka.voyager.common.audiocontroller.api

import io.mishka.voyager.common.audiocontroller.api.models.PlaybackState
import io.mishka.voyager.common.audiocontroller.api.models.PodcastPlaybackInfo
import kotlinx.coroutines.flow.StateFlow

interface IAudioController {

    /**
     * Current playback info
     */
    val playbackInfo: StateFlow<PodcastPlaybackInfo?>

    /**
     * Current playback state
     */
    val playbackState: StateFlow<PlaybackState>

    /**
     * Load and play podcast
     * @param audioFullPath Path in Supabase Storage format "bucket/file" (e.g., "podcasts/CM.wav")
     */
    suspend fun loadAndPlay(
        podcastId: String,
        audioFullPath: String,
        title: String,
        subtitle: String,
        durationSec: Int
    )

    /**
     * Play current podcast
     */
    suspend fun play()

    /**
     * Pause current podcast
     */
    suspend fun pause()

    /**
     * Stop and clear current podcast
     */
    suspend fun stop()

    /**
     * Seek to specific position in seconds
     */
    suspend fun seekTo(positionSec: Int)

    /**
     * Seek forward by seconds (default 10)
     */
    suspend fun seekForward(seconds: Int = 10)

    /**
     * Seek backward by seconds (default 10)
     */
    suspend fun seekBackward(seconds: Int = 10)

    /**
     * Get current position in seconds
     */
    fun getCurrentPosition(): Int

    /**
     * Check if podcast with given ID is currently playing
     */
    fun isPlaying(podcastId: String): Boolean
}
