package io.mishka.voyager.core.storage.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import io.mishka.voyager.core.repositories.countries.api.datasource.CountryDao
import io.mishka.voyager.core.repositories.countries.api.datasource.UserCountryDao
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryEntity
import io.mishka.voyager.core.repositories.countries.api.models.local.UserCountryEntity
import io.mishka.voyager.core.repositories.countrydetails.api.datasource.CountryAiSuggestDao
import io.mishka.voyager.core.repositories.countrydetails.api.datasource.CountryBestTimeDao
import io.mishka.voyager.core.repositories.countrydetails.api.datasource.CountryOverviewDao
import io.mishka.voyager.core.repositories.countrydetails.api.datasource.CountryPodcastDao
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryAiSuggestEntity
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryBestTimeEntity
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryOverviewEntity
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryPodcastEntity
import io.mishka.voyager.core.repositories.userpreferences.api.datasource.PrefDao
import io.mishka.voyager.core.repositories.userpreferences.api.models.local.PrefEntity
import io.mishka.voyager.core.repositories.userstats.api.datasource.UserStatsDao
import io.mishka.voyager.core.repositories.userstats.api.models.local.UserStatsEntity
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
        // Prefs
        PrefEntity::class,

        // User Stats
        UserStatsEntity::class,

        // Countries
        CountryEntity::class,
        UserCountryEntity::class,

        // Country Details
        CountryAiSuggestEntity::class,
        CountryBestTimeEntity::class,
        CountryOverviewEntity::class,
        CountryPodcastEntity::class,

        // Vibes
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

    // User Stats
    abstract fun userStatsDao(): UserStatsDao

    // Countries
    abstract fun countryDao(): CountryDao
    abstract fun userCountryDao(): UserCountryDao

    // Country Details
    abstract fun countryAiSuggestDao(): CountryAiSuggestDao
    abstract fun countryBestTimeDao(): CountryBestTimeDao
    abstract fun countryOverviewDao(): CountryOverviewDao
    abstract fun countryPodcastDao(): CountryPodcastDao

    // Vibes
    abstract fun vibeCategoryDao(): VibeCategoryDao
    abstract fun vibeDao(): VibeDao
    abstract fun vibesCountryDao(): VibesCountryDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
