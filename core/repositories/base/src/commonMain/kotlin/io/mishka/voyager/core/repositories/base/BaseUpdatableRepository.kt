package io.mishka.voyager.core.repositories.base

import io.mishka.voyager.time.api.ITimeSaver
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Duration

abstract class BaseUpdatableRepository(
    private val key: String,
    private val timeSaver: ITimeSaver,
) : BaseRepository() {

    /**
     * Method for loading data depends on expiration time [delay]
     *
     * @throws NullPointerException if we can not get data from backend,
     * and local data is empty
     */
    suspend fun <T> updatableLoad(
        delay: Duration,
        timeKey: String = key + SAVE_TIME_SUFFIX,
        forceUpdate: Boolean = false,
        remoteLoad: suspend () -> T,
        localLoad: suspend () -> T?,
        replaceLocalData: suspend (T) -> Unit,
    ): Result<T> {
        val generalLogPrefix = "updatableLoad($key, $timeKey)"

        val timeLimitPassed = isPassedTimeLimit(timeKey, delay)
        val timeHasPassed = timeLimitPassed == null || timeLimitPassed

        logger.d { "$generalLogPrefix: timeHasPassed - $timeHasPassed, forceUpdate - $forceUpdate" }

        val getFromBackend: suspend () -> Result<T> = {
            retryAction {
                val result = remoteLoad()
                replaceLocalData(result)
                timeSaver.saveCurrentTime(timeKey)
                result
            }
        }

        val getFromLocal: suspend () -> Result<T> = {
            runCatching {
                checkNotNull(localLoad()) { "$generalLogPrefix: Failed to get local data" }
            }
        }

        return try {
            if (timeHasPassed || forceUpdate) {
                // It is first time or time has passed
                // Let's load from remote
                logger.d { "$generalLogPrefix: Load from remote" }

                getFromBackend()
            } else {
                try {
                    // Time has not passed, let's load from local
                    logger.d { "$generalLogPrefix: Load from local" }

                    getFromLocal()
                } catch (e: Exception) {
                    logger.d {
                        "$generalLogPrefix: Failed to fetch data from local, try force update from backend - $e"
                    }
                    getFromBackend()
                }
            }
        } catch (e: Exception) {
            // Fallback to local
            logger.e { "$generalLogPrefix: Failed to load data, exception - $e" }
            getFromLocal()
        }
    }

    /**
     * Check is [delay] time passed
     *
     * @return true, if [delay] is LOWER than delta between current time and last saved by [timeKey]
     * @return false, if [delay] is BIGGER OR EQUAL than delta between current time and last saved by [timeKey]
     * @return null, if time has not saved yet
     */
    private suspend fun isPassedTimeLimit(
        timeKey: String,
        delay: Duration,
    ): Boolean? {
        logger.d { "isPassedTimeLimit($timeKey): delay - $delay" }
        val savedTime = timeSaver
            .getLastSavedTime(timeKey)
            ?.toInstant(TimeZone.currentSystemDefault())
            ?: return null

        val currentTime = timeSaver.getCurrentTime().toInstant(TimeZone.currentSystemDefault())
        val delta = currentTime - savedTime

        return delta > delay
    }

    private companion object {
        const val SAVE_TIME_SUFFIX = "_saved_time"
    }
}
