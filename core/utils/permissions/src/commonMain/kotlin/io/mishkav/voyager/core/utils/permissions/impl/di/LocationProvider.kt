package io.mishkav.voyager.core.utils.permissions.impl.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.mishkav.voyager.core.utils.context.VoyagerPlatformContext
import io.mishkav.voyager.core.utils.permissions.impl.location.ILocationController
import io.mishkav.voyager.core.utils.permissions.impl.location.getLocationController

@ContributesTo(AppScope::class)
interface LocationProvider {

    @SingleIn(AppScope::class)
    @Provides
    fun provideLocationController(context: VoyagerPlatformContext): ILocationController {
        return getLocationController(context)
    }
}
