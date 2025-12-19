package io.mishkav.voyager.core.debug

actual fun isDebuggable(): Boolean {
    return BuildConfig.DEBUG
}
