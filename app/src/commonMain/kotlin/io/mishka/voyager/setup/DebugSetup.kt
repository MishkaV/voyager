package io.mishka.voyager.setup

import co.touchlab.kermit.Logger
import co.touchlab.kermit.platformLogWriter
import io.mishkav.voyager.core.debug.isDebuggable

internal fun setupDebugInstruments() {
    setupLogger()
}

internal fun setupLogger() {
    if (isDebuggable()) {
        Logger.setLogWriters(platformLogWriter())
    } else {
        // TODO Add CrashlyticsAntilog for production
        // Logger.base(CrashlyticsAntilog())
    }
}
