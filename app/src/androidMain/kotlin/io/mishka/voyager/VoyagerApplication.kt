package io.mishka.voyager

import android.app.Application
import dev.zacsweers.metro.createGraph
import dev.zacsweers.metrox.android.MetroAppComponentProviders
import dev.zacsweers.metrox.android.MetroApplication
import io.mishka.voyager.di.VoyagerGraph

class VoyagerApplication : Application(), MetroApplication {

    override val appComponentProviders: MetroAppComponentProviders by lazy { createGraph<VoyagerGraph>() }
}
