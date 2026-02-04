package io.mishka.voyager.core.repositories.base

/**
 * Marker interface for repositories that support data sync/preloading.
 */
interface Syncable {
    /**
     * Preload data into cache.
     */
    suspend fun sync()
}
