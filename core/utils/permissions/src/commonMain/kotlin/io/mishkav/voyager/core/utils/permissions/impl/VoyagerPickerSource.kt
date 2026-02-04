package io.mishkav.voyager.core.utils.permissions.impl

enum class VoyagerPickerSource {
    LOCATION,
}

enum class VoyagerPermissionState {
    NOT_DETERMINED,

    NOT_GRANTED,

    GRANTED,

    DENIED,

    DENIED_ALWAYS,
}
