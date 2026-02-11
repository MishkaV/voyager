package io.mishkav.voyager.core.utils.permissions.impl.location

data class LocationCoordinates(
    val latitude: Double,
    val longitude: Double,
)

interface ILocationController {
    /**
     * Get current device location coordinates
     * @return LocationCoordinates or null if location is unavailable
     */
    suspend fun getCurrentLocation(): LocationCoordinates?
}
