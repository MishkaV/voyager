package io.mishka.voyager.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import io.mishkav.voyager.core.utils.context.VoyagerPlatformContext
import io.mishkav.voyager.features.navigation.api.RootComponent

@DependencyGraph(AppScope::class)
interface VoyagerJVMAppGraph {
    val rootComponentFactory: RootComponent.Factory

    @Provides
    fun providePlatformContext(): VoyagerPlatformContext {
        return VoyagerPlatformContext.INSTANCE
    }
}
