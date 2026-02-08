package io.mishka.voyager.common.audiocontroller.api.models

data class PodcastPlaybackInfo(
    val podcastId: String,
    val audioFullPath: String,
    val title: String,
    val subtitle: String,
    val durationSec: Int,
    val currentPositionSec: Int,
    val playbackState: PlaybackState
)
