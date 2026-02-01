package io.mishka.voyager.core.storage.settings.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.datastore.DataStoreSettings
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.mishkav.voyager.core.utils.context.VoyagerPlatformContext

@ContributesTo(AppScope::class)
interface SettingsProviders {

    @OptIn(ExperimentalSettingsApi::class)
    @SingleIn(AppScope::class)
    @Provides
    fun provideSettings(dataStore: DataStore<Preferences>): SuspendSettings {
        return DataStoreSettings(dataStore)
    }

    @SingleIn(AppScope::class)
    @Provides
    fun provideDataStore(context: VoyagerPlatformContext): DataStore<Preferences> {
        return providePlatformDataStore(context)
    }
}

expect fun providePlatformDataStore(context: VoyagerPlatformContext): DataStore<Preferences>
