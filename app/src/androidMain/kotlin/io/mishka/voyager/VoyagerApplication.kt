package io.mishka.voyager

import android.app.Application
import android.os.StrictMode
import dev.zacsweers.metro.createGraph
import dev.zacsweers.metrox.android.MetroAppComponentProviders
import dev.zacsweers.metrox.android.MetroApplication
import io.mishka.voyager.di.VoyagerGraph
import io.mishka.voyager.setup.setupInstruments
import io.mishkav.voyager.core.debug.isDebuggable

class VoyagerApplication : Application(), MetroApplication {

    override val appComponentProviders: MetroAppComponentProviders by lazy { createGraph<VoyagerGraph>() }

    override fun onCreate() {
        super.onCreate()

        setStrictModePolicy()
        setupInstruments()
    }

    private fun setStrictModePolicy() {
        if (isDebuggable()) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDropBox()
                    .build(),
            )

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDropBox()
                    .build()
            )
        }
    }
}
