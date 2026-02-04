package io.mishkav.voyager.core.utils.permissions.impl

import androidx.compose.runtime.Composable

@Composable
expect fun rememberVoyagerPermissionHandler(): VoyagerPermissionHandler

expect class VoyagerPermissionHandler {

    suspend fun providePermission(pickerSource: VoyagerPickerSource)

    suspend fun isPermissionGranted(pickerSource: VoyagerPickerSource): Boolean

    suspend fun getPermissionState(pickerSource: VoyagerPickerSource): VoyagerPermissionState

    fun openAppSettings()
}
