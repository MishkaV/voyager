package io.mishka.voyager.common.audiocontroller.impl.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.mishka.voyager.common.audiocontroller.api.IAudioController
import io.mishka.voyager.common.audiocontroller.impl.AudioController
import io.mishka.voyager.common.audiocontroller.impl.utils.SupabaseAudioUrlResolver
import io.mishkav.voyager.core.utils.context.VoyagerPlatformContext

@ContributesTo(AppScope::class)
interface AudioControllerProviders {

    @SingleIn(AppScope::class)
    @Provides
    fun provideAudioController(
        context: VoyagerPlatformContext,
        audioUrlResolver: SupabaseAudioUrlResolver
    ): IAudioController {
        return AudioController(context, audioUrlResolver)
    }
}
