package io.mishka.voyager.core.storage.database.builder

import androidx.room.Room
import androidx.room.RoomDatabase
import io.mishka.voyager.core.storage.database.AppDatabase
import io.mishka.voyager.core.storage.database.DATABASE_NAME
import io.mishkav.voyager.core.utils.context.VoyagerPlatformContext
import java.io.File

/**
 * JVM/Desktop-specific database builder factory.
 * Uses temporary directory for database file.
 */
actual class AppDatabaseBuilderFactory {
    actual fun getDatabaseBuilder(context: VoyagerPlatformContext): RoomDatabase.Builder<AppDatabase> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), DATABASE_NAME)
        return Room.databaseBuilder<AppDatabase>(
            name = dbFile.absolutePath,
        )
    }
}
