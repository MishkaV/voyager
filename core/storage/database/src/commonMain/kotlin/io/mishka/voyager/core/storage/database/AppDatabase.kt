package io.mishka.voyager.core.storage.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import io.mishka.voyager.core.repositories.userpreferences.api.datasource.PrefDao
import io.mishka.voyager.core.repositories.userpreferences.api.models.local.PrefEntity
import io.mishka.voyager.core.repositories.vibes.api.datasource.VibeCategoryDao
import io.mishka.voyager.core.repositories.vibes.api.datasource.VibeDao
import io.mishka.voyager.core.repositories.vibes.api.datasource.VibesCountryDao
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeCategoryEntity
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeEntity
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibesCountryEntity

internal const val DATABASE_VERSION = 1
internal const val DATABASE_NAME = "voyager-database.db"

@Database(
    entities = [
        PrefEntity::class,
        VibeCategoryEntity::class,
        VibeEntity::class,
        VibesCountryEntity::class,
    ],
    version = DATABASE_VERSION,
    exportSchema = true,
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {

    // Prefs
    abstract fun prefDao(): PrefDao

    // Vibes
    abstract fun vibeCategoryDao(): VibeCategoryDao
    abstract fun vibeDao(): VibeDao
    abstract fun vibesCountryDao(): VibesCountryDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
