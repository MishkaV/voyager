package io.mishka.voyager.core.repositories.base

/**
 * Marker interface for class that store user-specific data locally.
 */
interface Cleanable {
    /**
     * Remove all user-specific data from local storage.
     */
    suspend fun cleanup()
}
