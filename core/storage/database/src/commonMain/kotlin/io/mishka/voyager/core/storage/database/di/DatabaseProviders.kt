package io.mishka.voyager.core.storage.database.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.mishka.voyager.core.repositories.userpreferences.api.datasource.PrefDao
import io.mishka.voyager.core.repositories.userstats.api.datasource.UserStatsDao
import io.mishka.voyager.core.repositories.vibes.api.datasource.VibeCategoryDao
import io.mishka.voyager.core.repositories.vibes.api.datasource.VibeDao
import io.mishka.voyager.core.repositories.vibes.api.datasource.VibesCountryDao
import io.mishka.voyager.core.storage.database.AppDatabase
import io.mishka.voyager.core.storage.database.builder.buildRoomDatabase
import io.mishkav.voyager.core.utils.context.VoyagerPlatformContext

/**
 * Metro DI providers for Room database.
 */
@ContributesTo(AppScope::class)
interface DatabaseProviders {

    @SingleIn(AppScope::class)
    @Provides
    fun provideDatabase(
        context: VoyagerPlatformContext,
    ): AppDatabase {
        return buildRoomDatabase(context)
    }

    @Provides
    fun providePrefDao(
        database: AppDatabase
    ): PrefDao = database.prefDao()

    @Provides
    fun provideUserStatsDao(
        database: AppDatabase
    ): UserStatsDao = database.userStatsDao()

    @Provides
    fun provideVibeCategoryDao(
        database: AppDatabase
    ): VibeCategoryDao = database.vibeCategoryDao()

    @Provides
    fun provideVibeDao(
        database: AppDatabase
    ): VibeDao = database.vibeDao()

    @Provides
    fun provideVibesCountryDao(
        database: AppDatabase
    ): VibesCountryDao = database.vibesCountryDao()
}
