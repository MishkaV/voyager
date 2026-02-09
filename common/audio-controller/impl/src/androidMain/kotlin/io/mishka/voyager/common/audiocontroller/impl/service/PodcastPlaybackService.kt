package io.mishka.voyager.common.audiocontroller.impl.service

import android.os.Bundle
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import co.touchlab.kermit.Logger
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.ListenableFuture

/**
 * MediaSessionService for background audio playback
 * Handles playback controls and notifications with custom buttons
 */
class PodcastPlaybackService : MediaSessionService() {

    private var mediaSession: MediaSession? = null
    private var player: ExoPlayer? = null

    override fun onCreate() {
        super.onCreate()
        Logger.d { "PodcastPlaybackService: onCreate" }

        initializeSessionAndPlayer()
    }

    override fun onDestroy() {
        Logger.d { "PodcastPlaybackService: onDestroy" }

        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        player = null

        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    @OptIn(UnstableApi::class)
    private fun initializeSessionAndPlayer() {
        // Initialize ExoPlayer with audio focus handling
        player = ExoPlayer.Builder(this)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(C.AUDIO_CONTENT_TYPE_SPEECH)
                    .setUsage(C.USAGE_MEDIA)
                    .build(),
                /* handleAudioFocus = */
                true
            )
            .build()

        // Create MediaSession with custom commands
        mediaSession = MediaSession.Builder(this, player!!)
            .setCallback(MediaSessionCallback())
            .build()

        Logger.d { "PodcastPlaybackService: MediaSession initialized" }
    }

    /**
     * Callback for MediaSession events
     */
    private inner class MediaSessionCallback : MediaSession.Callback {
        override fun onConnect(
            session: MediaSession,
            controller: MediaSession.ControllerInfo
        ): MediaSession.ConnectionResult {
            Logger.d { "PodcastPlaybackService: Controller connected: ${controller.packageName}" }

            val connectionResult = super.onConnect(session, controller)
            val sessionCommands = connectionResult.availableSessionCommands.buildUpon()

            // Add custom commands for skip forward/backward
            sessionCommands.add(SessionCommand(COMMAND_SKIP_FORWARD, Bundle.EMPTY))
            sessionCommands.add(SessionCommand(COMMAND_SKIP_BACKWARD, Bundle.EMPTY))

            return MediaSession.ConnectionResult.accept(
                sessionCommands.build(),
                connectionResult.availablePlayerCommands
            )
        }

        override fun onCustomCommand(
            session: MediaSession,
            controller: MediaSession.ControllerInfo,
            customCommand: SessionCommand,
            args: Bundle
        ): ListenableFuture<SessionResult> {
            when (customCommand.customAction) {
                COMMAND_SKIP_FORWARD -> {
                    player?.let {
                        val currentPosition = it.currentPosition
                        val newPosition = currentPosition + SKIP_DURATION_MS
                        it.seekTo(newPosition)
                        Logger.d { "PodcastPlaybackService: Skip forward to ${newPosition}ms" }
                    }
                }
                COMMAND_SKIP_BACKWARD -> {
                    player?.let {
                        val currentPosition = it.currentPosition
                        val newPosition = (currentPosition - SKIP_DURATION_MS).coerceAtLeast(0)
                        it.seekTo(newPosition)
                        Logger.d { "PodcastPlaybackService: Skip backward to ${newPosition}ms" }
                    }
                }
            }
            return super.onCustomCommand(session, controller, customCommand, args)
        }

        @OptIn(UnstableApi::class)
        override fun onPostConnect(session: MediaSession, controller: MediaSession.ControllerInfo) {
            super.onPostConnect(session, controller)

            // Set custom layout for notification
            if (controller.controllerVersion != 0) {
                val customLayout = getCustomLayout()
                session.setCustomLayout(controller, customLayout)
            }
        }

        @OptIn(UnstableApi::class)
        private fun getCustomLayout(): ImmutableList<CommandButton> {
            val res = this@PodcastPlaybackService.resources
            val packageName = this@PodcastPlaybackService.packageName

            // Try to find custom icons, fallback to system icons if not found
            var icPrevId = res.getIdentifier("ic_prev_24", "drawable", packageName)
            var icNextId = res.getIdentifier("ic_next_24", "drawable", packageName)

            // Fallback to Android system icons if custom icons not found
            if (icPrevId == 0) {
                icPrevId = android.R.drawable.ic_media_rew
                Logger.w { "Custom icon ic_prev_24 not found, using system icon" }
            }
            if (icNextId == 0) {
                icNextId = android.R.drawable.ic_media_ff
                Logger.w { "Custom icon ic_next_24 not found, using system icon" }
            }

            return ImmutableList.of(
                // Skip backward button (-10s)
                CommandButton.Builder()
                    .setDisplayName("Skip backward")
                    .setSessionCommand(SessionCommand(COMMAND_SKIP_BACKWARD, Bundle.EMPTY))
                    .setIconResId(icPrevId)
                    .build(),
                // Play/Pause is handled automatically by MediaSession
                // Skip forward button (+10s)
                CommandButton.Builder()
                    .setDisplayName("Skip forward")
                    .setSessionCommand(SessionCommand(COMMAND_SKIP_FORWARD, Bundle.EMPTY))
                    .setIconResId(icNextId)
                    .build()
            )
        }
    }

    companion object {
        const val COMMAND_SKIP_FORWARD = "io.mishka.voyager.SKIP_FORWARD"
        const val COMMAND_SKIP_BACKWARD = "io.mishka.voyager.SKIP_BACKWARD"

        private const val SKIP_DURATION_MS = 10_000L // 10 seconds
    }
}
