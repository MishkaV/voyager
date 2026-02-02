package io.mishka.voyager.core.storage.settings

import android.app.Application
import okio.Path
import okio.Path.Companion.toPath

actual class DataStorePathFactory(
    private val application: Application,
) {
    actual fun providePath(): Path {
        return application.filesDir.resolve(DATA_STORE_NAME).absolutePath.toPath()
    }
}
