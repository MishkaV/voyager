package io.mishka.voyager.profile.impl.api

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.binding
import io.mishka.voyager.profile.api.ProfileComponent
import io.mishka.voyager.profile.impl.ui.ProfileScreen
import io.mishka.voyager.profile.impl.ui.ProfileViewModel
import io.mishkav.voyager.core.ui.lifecycle.viewModelWithFactory

@AssistedInject
class ProfileComponentImpl(
    @Assisted componentContext: ComponentContext,
    private val profileViewModelProvider: Provider<ProfileViewModel>,
) : ProfileComponent(componentContext), ComponentContext by componentContext, BackHandlerOwner {

    @Composable
    override fun Render() {
        val viewModel = viewModelWithFactory {
            profileViewModelProvider()
        }

        ProfileScreen(
            viewModel = viewModel,
        )
    }

    @AssistedFactory
    @ContributesBinding(AppScope::class, binding<ProfileComponent.Factory>())
    interface Factory : ProfileComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
        ): ProfileComponentImpl
    }
}
