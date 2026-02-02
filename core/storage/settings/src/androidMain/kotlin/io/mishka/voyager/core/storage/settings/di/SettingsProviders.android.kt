package io.mishka.voyager.core.storage.settings.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import io.mishka.voyager.core.storage.settings.DataStorePathFactory
import io.mishkav.voyager.core.utils.context.VoyagerPlatformContext

actual fun providePlatformDataStore(context: VoyagerPlatformContext): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath {
        DataStorePathFactory(context as Application).providePath()
    }
}
