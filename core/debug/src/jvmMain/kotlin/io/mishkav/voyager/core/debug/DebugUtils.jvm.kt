package io.mishkav.voyager.core.debug

/**
 * JVM implementation that checks the `debuggable` system property.
 *
 * Returns `true` when the JVM property `debuggable` is set to `"true"`.
 * If the property is absent, this defaults to `true`.
 */
actual fun isDebuggable(): Boolean {
    return (System.getProperty("debuggable") ?: "true").toBoolean()
}
