package io.mishka.voyager.core.storage.database.builder

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import io.mishka.voyager.core.storage.database.AppDatabase
import io.mishkav.voyager.core.utils.context.VoyagerPlatformContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

/**
 * Builds the Room database instance with platform-specific configuration.
 *
 * @return configured AppDatabase instance
 */
internal fun buildRoomDatabase(
    context: VoyagerPlatformContext
): AppDatabase = AppDatabaseBuilderFactory()
    .getDatabaseBuilder(context)
    .setDriver(BundledSQLiteDriver())
    .setQueryCoroutineContext(Dispatchers.IO)
    .fallbackToDestructiveMigration(true)
    .build()

expect class AppDatabaseBuilderFactory() {
    fun getDatabaseBuilder(context: VoyagerPlatformContext): RoomDatabase.Builder<AppDatabase>
}
