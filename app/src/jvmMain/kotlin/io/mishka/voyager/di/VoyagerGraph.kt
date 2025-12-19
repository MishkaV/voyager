package io.mishka.voyager.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import io.mishkav.voyager.features.navigation.api.RootComponent

@DependencyGraph(AppScope::class)
interface VoyagerGraph {
    val rootComponentFactory: RootComponent.Factory
}
