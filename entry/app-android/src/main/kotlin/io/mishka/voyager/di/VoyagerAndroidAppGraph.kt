package io.mishka.voyager.di

import android.app.Application
import android.content.Context
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metrox.android.MetroAppComponentProviders
import dev.zacsweers.metrox.viewmodel.ViewModelGraph
import io.github.jan.supabase.SupabaseClient

@DependencyGraph(AppScope::class)
interface VoyagerAndroidAppGraph : MetroAppComponentProviders, ViewModelGraph {

    val supabase: SupabaseClient

    @Provides
    fun provideApplicationContext(application: Application): Context = application

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(@Provides application: Application): VoyagerAndroidAppGraph
    }
}
