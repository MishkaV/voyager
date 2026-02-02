package io.mishka.voyager

import android.app.Application
import android.os.StrictMode
import dev.zacsweers.metro.createGraphFactory
import dev.zacsweers.metrox.android.MetroAppComponentProviders
import dev.zacsweers.metrox.android.MetroApplication
import io.mishka.voyager.di.VoyagerAndroidAppGraph
import io.mishka.voyager.setup.setupInstruments
import io.mishkav.voyager.BuildConfig

class VoyagerApplication : Application(), MetroApplication {

    private val appGraph by lazy { createGraphFactory<VoyagerAndroidAppGraph.Factory>().create(this) }

    override val appComponentProviders: MetroAppComponentProviders
        get() = appGraph

    val isDebuggable = BuildConfig.DEBUG

    override fun onCreate() {
        super.onCreate()

        setStrictModePolicy()
        setupInstruments(isDebuggable)
    }

    private fun setStrictModePolicy() {
        if (isDebuggable) {
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
