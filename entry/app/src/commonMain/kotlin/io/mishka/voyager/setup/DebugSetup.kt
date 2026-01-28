package io.mishka.voyager.setup

import co.touchlab.kermit.Logger
import co.touchlab.kermit.platformLogWriter

internal fun setupDebugInstruments(isDebuggable: Boolean) {
    setupLogger(isDebuggable)
}

internal fun setupLogger(isDebuggable: Boolean) {
    Logger.setTag("Voyager")

    if (isDebuggable) {
        Logger.setLogWriters(platformLogWriter())
    } else {
        // TODO Add CrashlyticsAntilog for production
        // Logger.base(CrashlyticsAntilog())
    }
}
