package io.mishka.voyager.location.impl.api

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.binding
import io.mishka.voyager.location.api.LocationComponent
import io.mishka.voyager.location.impl.ui.LocationScreen

@AssistedInject
class LocationComponentImpl(
    @Assisted componentContext: ComponentContext,
    @Assisted private val onRequestLocation: (isGranted: Boolean) -> Unit,
    @Assisted private val onBack: () -> Unit,
) : LocationComponent(componentContext),
    ComponentContext by componentContext,
    BackHandlerOwner {
    @Composable
    override fun Render() {
        LocationScreen(
            onRequestLocation = onRequestLocation,
            onBack = onBack,
        )
    }

    @AssistedFactory
    @ContributesBinding(AppScope::class, binding<LocationComponent.Factory>())
    interface Factory : LocationComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onRequestLocation: (isGranted: Boolean) -> Unit,
            onBack: () -> Unit,
        ): LocationComponentImpl
    }
}
