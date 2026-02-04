package io.mishkav.voyager.core.utils.permissions.impl

import co.touchlab.kermit.Logger
import io.mishkav.voyager.core.utils.permissions.impl.exceptions.VoyagerDeniedAlwaysException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.actionWithPermission(
    pickerSource: VoyagerPickerSource,
    permissionHandler: VoyagerPermissionHandler,
    onGranted: suspend () -> Unit,
    onAlwaysDenied: suspend () -> Unit,
) {
    launch {
        when {
            !shouldAskPermission(pickerSource) -> onGranted()

            permissionHandler.isPermissionGranted(pickerSource) -> onGranted()

            else -> {
                try {
                    val permissionState = permissionHandler.getPermissionState(pickerSource)

                    Logger.d("actionWithPermission(): permissionState - $permissionState")

                    if (permissionState == VoyagerPermissionState.DENIED_ALWAYS) {
                        onAlwaysDenied()
                    } else {
                        permissionHandler.providePermission(pickerSource)
                        // Permission granted, let's do main action
                        onGranted()
                    }
                } catch (e: VoyagerDeniedAlwaysException) {
                    Logger.w("actionWithPermission(): DeniedAlwaysException exception - $e")
                    onAlwaysDenied()
                } catch (e: Exception) {
                    Logger.e("actionWithPermission(): general exception - $e")
                    // Just intercept
                }
            }
        }
    }
}

internal expect fun shouldAskPermission(permission: VoyagerPickerSource): Boolean
