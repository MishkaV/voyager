package io.mishka.voyager.core.storage.database.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.mishka.voyager.core.repositories.userpreferences.api.datasource.PrefDao
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
}
