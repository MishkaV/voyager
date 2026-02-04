package io.mishkav.voyager.core.utils.permissions.impl

import androidx.compose.runtime.Composable

@Composable
actual fun rememberVoyagerPermissionHandler(): VoyagerPermissionHandler {
    return VoyagerPermissionHandler()
}

actual class VoyagerPermissionHandler {
    actual suspend fun providePermission(pickerSource: VoyagerPickerSource) {
        // Ignore
    }

    actual suspend fun isPermissionGranted(pickerSource: VoyagerPickerSource): Boolean = true

    actual suspend fun getPermissionState(
        pickerSource: VoyagerPickerSource
    ): VoyagerPermissionState = VoyagerPermissionState.GRANTED

    actual fun openAppSettings() {
        error("Not supported opening settings on this platform")
    }
}
