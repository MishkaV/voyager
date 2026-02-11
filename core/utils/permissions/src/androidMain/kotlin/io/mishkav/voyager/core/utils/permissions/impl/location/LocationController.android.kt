package io.mishkav.voyager.core.utils.permissions.impl.location

import android.annotation.SuppressLint
import android.location.Location
import co.touchlab.kermit.Logger
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import io.mishkav.voyager.core.utils.context.VoyagerPlatformContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual fun getLocationController(context: VoyagerPlatformContext): ILocationController {
    return AndroidLocationController(
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    )
}

class AndroidLocationController(
    private val fusedLocationClient: FusedLocationProviderClient,
) : ILocationController {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): LocationCoordinates? {
        return try {
            // Try to get current location first
            var location = suspendCoroutine { continuation ->
                val cancellationTokenSource = CancellationTokenSource()
                Logger.d("LocationController: Getting current location")

                fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    cancellationTokenSource.token
                ).addOnSuccessListener { location: Location? ->
                    Logger.d("LocationController: Success to get current location - $location")
                    continuation.resume(location)
                }.addOnFailureListener { exception ->
                    Logger.e("LocationController: Failed to get current location", exception)
                    continuation.resume(null)
                }
            }

            // Fallback to last known location if current location is null
            if (location == null) {
                Logger.d("LocationController: Current location is null, trying last known location")
                location = suspendCoroutine { continuation ->
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { lastLocation: Location? ->
                            Logger.d("LocationController: Last known location - $lastLocation")
                            continuation.resume(lastLocation)
                        }
                        .addOnFailureListener { exception ->
                            Logger.e("LocationController: Failed to get last location", exception)
                            continuation.resume(null)
                        }
                }
            }

            location?.let {
                Logger.d("LocationController: Returning coordinates: ${it.latitude}, ${it.longitude}")
                LocationCoordinates(
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            } ?: run {
                Logger.e("LocationController: No location available")
                null
            }
        } catch (e: Exception) {
            Logger.e("LocationController: Error getting location", e)
            null
        }
    }
}
