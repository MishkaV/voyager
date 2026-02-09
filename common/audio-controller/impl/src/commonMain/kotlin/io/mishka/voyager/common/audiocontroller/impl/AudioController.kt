package io.mishka.voyager.common.audiocontroller.impl

import io.mishka.voyager.common.audiocontroller.api.IAudioController
import io.mishka.voyager.common.audiocontroller.impl.utils.SupabaseAudioUrlResolver
import io.mishkav.voyager.core.utils.context.VoyagerPlatformContext

expect class AudioController(
    context: VoyagerPlatformContext,
    audioUrlResolver: SupabaseAudioUrlResolver
) : IAudioController
