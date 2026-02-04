package io.mishkav.voyager.core.utils.permissions.impl.utils

import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.location.COARSE_LOCATION
import io.mishkav.voyager.core.utils.permissions.impl.VoyagerPermissionState
import io.mishkav.voyager.core.utils.permissions.impl.VoyagerPickerSource

internal fun VoyagerPickerSource.toMoko(): Permission = when (this) {
    VoyagerPickerSource.LOCATION -> Permission.COARSE_LOCATION
}

internal fun PermissionState.toVoyager(): VoyagerPermissionState = when (this) {
    PermissionState.NotDetermined -> VoyagerPermissionState.NOT_DETERMINED
    PermissionState.NotGranted -> VoyagerPermissionState.NOT_GRANTED
    PermissionState.Granted -> VoyagerPermissionState.GRANTED
    PermissionState.Denied -> VoyagerPermissionState.DENIED
    PermissionState.DeniedAlways -> VoyagerPermissionState.DENIED_ALWAYS
}

internal fun VoyagerPermissionState.toMoko(): PermissionState = when (this) {
    VoyagerPermissionState.NOT_DETERMINED -> PermissionState.NotDetermined
    VoyagerPermissionState.NOT_GRANTED -> PermissionState.NotGranted
    VoyagerPermissionState.GRANTED -> PermissionState.Granted
    VoyagerPermissionState.DENIED -> PermissionState.Denied
    VoyagerPermissionState.DENIED_ALWAYS -> PermissionState.DeniedAlways
}
