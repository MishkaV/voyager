package io.mishka.voyager

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import dev.zacsweers.metro.createGraph
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.coil.coil3
import io.mishka.voyager.di.VoyagerJVMAppGraph
import io.mishka.voyager.setup.setupInstruments
import io.mishkav.voyager.core.debug.isDebuggable
import io.mishkav.voyager.features.navigation.api.model.VoyagerStartupStatus
import io.mishkav.voyager.features.navigation.impl.ui.RootComposePoint

@OptIn(SupabaseExperimental::class)
fun main() {
    val appGraph = createGraph<VoyagerJVMAppGraph>()

    setupInstruments(
        isDebuggable = isDebuggable(),
        supabaseCoil = appGraph.supabase.coil3,
    )

    val backDispatcher = BackDispatcher()
    val lifecycle = LifecycleRegistry()

    val root = appGraph.rootComponentFactory.create(
        componentContext = DefaultComponentContext(lifecycle),
        backHandler = backDispatcher,
        startupStatus = VoyagerStartupStatus.ShouldShowIntro,
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
