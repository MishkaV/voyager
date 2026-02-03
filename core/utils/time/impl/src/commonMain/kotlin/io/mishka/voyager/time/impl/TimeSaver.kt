package io.mishka.voyager.time.impl

import co.touchlab.kermit.Logger
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import io.mishka.voyager.time.api.ITimeSaver
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

@OptIn(ExperimentalSettingsApi::class)
@ContributesBinding(AppScope::class)
class TimeSaver(
    private val settings: SuspendSettings,
) : ITimeSaver {

    private val logger by lazy { Logger.withTag("TimeSaver") }

    override fun getCurrentTime(): LocalDateTime {
        logger.d { "getCurrentTime()" }
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    override suspend fun saveCurrentTime(key: String) {
        logger.d { "saveCurrentTime(): key - $key" }
        val currentTime = getCurrentTime()
        settings.putString(key, currentTime.toString())
    }

    override suspend fun resetTime(key: String) {
        logger.d { "resetTime(): key - $key" }
        settings.remove(key)
    }

    override suspend fun getLastSavedTime(key: String): LocalDateTime? {
        logger.d("getLastSavedTime(): key - $key")
        val lastSavedTime = settings.getStringOrNull(key) ?: return null
        return try {
            LocalDateTime.parse(lastSavedTime)
        } catch (e: Exception) {
            logger.e { "Failed to convert saved time by key - $key, $e" }
            null
        }
    }
}
