package io.mishka.voyager.core.storage.database.builder

import androidx.room.Room
import androidx.room.RoomDatabase
import io.mishka.voyager.core.storage.database.AppDatabase
import io.mishka.voyager.core.storage.database.DATABASE_NAME
import io.mishkav.voyager.core.utils.context.VoyagerPlatformContext

/**
 * Android-specific database builder factory.
 * Requires Android Context to determine database file path.
 */
actual class AppDatabaseBuilderFactory {
    actual fun getDatabaseBuilder(context: VoyagerPlatformContext): RoomDatabase.Builder<AppDatabase> {
        val dbFile = context.getDatabasePath(DATABASE_NAME)
        return Room.databaseBuilder<AppDatabase>(
            context = context,
            name = dbFile.absolutePath,
        )
    }
}
