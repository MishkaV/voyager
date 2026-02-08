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
 * Handles playback controls and notifications
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
                    }
                }
                COMMAND_SKIP_BACKWARD -> {
                    player?.let {
                        val currentPosition = it.currentPosition
                        val newPosition = (currentPosition - SKIP_DURATION_MS).coerceAtLeast(0)
                        it.seekTo(newPosition)
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
            return ImmutableList.of(
                // Skip backward button
                CommandButton.Builder()
                    .setDisplayName("Skip backward")
                    .setSessionCommand(SessionCommand(COMMAND_SKIP_BACKWARD, Bundle.EMPTY))
                    .setIconResId(android.R.drawable.ic_media_rew)
                    .build(),
                // Play/Pause is handled by default
                // Skip forward button
                CommandButton.Builder()
                    .setDisplayName("Skip forward")
                    .setSessionCommand(SessionCommand(COMMAND_SKIP_FORWARD, Bundle.EMPTY))
                    .setIconResId(android.R.drawable.ic_media_ff)
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
