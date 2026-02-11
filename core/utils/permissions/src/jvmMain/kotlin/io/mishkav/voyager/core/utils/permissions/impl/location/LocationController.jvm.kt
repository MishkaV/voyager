package io.mishkav.voyager.core.utils.permissions.impl.location

import co.touchlab.kermit.Logger
import io.mishkav.voyager.core.utils.context.VoyagerPlatformContext

actual fun getLocationController(context: VoyagerPlatformContext): ILocationController {
    return JvmLocationController()
}

class JvmLocationController : ILocationController {
    override suspend fun getCurrentLocation(): LocationCoordinates? {
        Logger.w("LocationController: Location services not available on JVM/Desktop")
        // For JVM/Desktop, we could return null or hardcoded coordinates for testing
        // Return null to indicate location is not available on this platform
        return null
    }
}
