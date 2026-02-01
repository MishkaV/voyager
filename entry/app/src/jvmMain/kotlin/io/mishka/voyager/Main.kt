package io.mishka.voyager

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import dev.zacsweers.metro.createGraph
import io.mishka.voyager.di.VoyagerGraph
import io.mishka.voyager.setup.setupInstruments
import io.mishkav.voyager.core.debug.isDebuggable
import io.mishkav.voyager.features.navigation.api.model.VoyagerStartupStatus
import io.mishkav.voyager.features.navigation.impl.ui.RootComposePoint

fun main() {
    setupInstruments(
        isDebuggable = isDebuggable()
    )

    val appGraph = createGraph<VoyagerGraph>()
    val backDispatcher = BackDispatcher()
    val lifecycle = LifecycleRegistry()

    val root = appGraph.rootComponentFactory.create(
        componentContext = DefaultComponentContext(lifecycle),
        backHandler = backDispatcher,
        startupStatus = VoyagerStartupStatus.ShouldLogin,
    )

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Voyager",
        ) {
            RootComposePoint(
                root = root,
            )
        }
    }
}
