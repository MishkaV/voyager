package io.mishkav.voyager.core.utils.permissions.impl

import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import co.touchlab.kermit.Logger
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.PermissionsController
import io.mishkav.voyager.core.utils.permissions.impl.exceptions.VoyagerDeniedAlwaysException
import io.mishkav.voyager.core.utils.permissions.impl.utils.toMoko
import io.mishkav.voyager.core.utils.permissions.impl.utils.toVoyager

@Composable
actual fun rememberVoyagerPermissionHandler(): VoyagerPermissionHandler {
    val permissionsController = rememberVoyagerPermissionsController()

    val activityResultRegistry = LocalActivityResultRegistryOwner.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(
        activityResultRegistry,
        lifecycleOwner,
        context,
    ) {
        permissionsController.bind(
            resultRegistry = checkNotNull(activityResultRegistry),
        )
    }

    return remember {
        VoyagerPermissionHandler(
            permissionsController = permissionsController,
        )
    }
}

actual class VoyagerPermissionHandler(
    public val permissionsController: PermissionsController,
) {
    public actual suspend fun providePermission(pickerSource: VoyagerPickerSource) {
        try {
            permissionsController.providePermission(pickerSource.toMoko())
        } catch (e: DeniedAlwaysException) {
            Logger.w("VoyagerPermissionHandler: always denied - $e")
            throw VoyagerDeniedAlwaysException()
        }
    }

    public actual suspend fun isPermissionGranted(pickerSource: VoyagerPickerSource): Boolean {
        return permissionsController.isPermissionGranted(pickerSource.toMoko())
    }
    public actual fun openAppSettings() {
        permissionsController.openAppSettings()
    }

    public actual suspend fun getPermissionState(pickerSource: VoyagerPickerSource): VoyagerPermissionState {
        return permissionsController.getPermissionState(pickerSource.toMoko()).toVoyager()
    }
}
