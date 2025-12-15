package io.mishka.voyager

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.zacsweers.metro.createGraph
import io.mishka.voyager.di.VoyagerGraph

fun main() {
    val appGraph = createGraph<VoyagerGraph>()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Voyager",
        ) {
            App()
        }
    }
}
