package io.mishka.voyager.time.api

import kotlinx.datetime.LocalDateTime

interface ITimeSaver {

    /**
     * Get current time
     */
    fun getCurrentTime(): LocalDateTime

    /**
     * Save current time to store
     *
     * @param key used for saving time at datastore
     */
    suspend fun saveCurrentTime(key: String)

    /**
     * Reset time in storage
     *
     * @param key used for saving time at datastore
     */
    suspend fun resetTime(key: String)

    /**
     * Get last saved time, if it is exist
     *
     * @param key used for getting time at datastore
     */
    suspend fun getLastSavedTime(key: String): LocalDateTime?
}
