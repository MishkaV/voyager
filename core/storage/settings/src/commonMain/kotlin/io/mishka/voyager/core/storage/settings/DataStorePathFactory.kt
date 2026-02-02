package io.mishka.voyager.core.storage.settings

import okio.Path

internal const val DATA_STORE_NAME = "voyager_preferences.preferences_pb"

expect class DataStorePathFactory {
    fun providePath(): Path
}
