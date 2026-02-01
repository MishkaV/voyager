package io.mishka.voyager.core.storage.settings

import okio.Path
import okio.Path.Companion.toPath
import java.io.File

actual class DataStorePathFactory() {
    actual fun providePath(): Path {
        val file = File(System.getProperty("java.io.tmpdir"), DATA_STORE_NAME)
        return file.absolutePath.toPath()
    }
}
