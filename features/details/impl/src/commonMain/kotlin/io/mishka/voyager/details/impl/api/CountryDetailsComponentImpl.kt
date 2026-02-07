package io.mishka.voyager.details.impl.api

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.binding
import io.mishka.voyager.details.api.CountryDetailsComponent
import io.mishka.voyager.details.api.models.CountryDetailsArgs
import io.mishka.voyager.details.impl.ui.CountryDetailsScreen
import io.mishka.voyager.details.impl.ui.CountryDetailsViewModel
import io.mishkav.voyager.core.ui.decompose.DecomposeOnBackParameter
import io.mishkav.voyager.core.ui.lifecycle.viewModelWithFactory

@AssistedInject
class CountryDetailsComponentImpl(
    @Assisted componentContext: ComponentContext,
    @Assisted private val args: CountryDetailsArgs,
    @Assisted private val navigateBack: DecomposeOnBackParameter,
    private val countryDetailsViewModelProvider: Provider<CountryDetailsViewModel>,
) : CountryDetailsComponent(componentContext), ComponentContext by componentContext {

    @Composable
    override fun Render() {
        val viewModel = viewModelWithFactory {
            countryDetailsViewModelProvider()
        }

        CountryDetailsScreen(
            args = args,
            viewModel = viewModel,
            navigateBack = navigateBack::invoke,
            modifier = Modifier.fillMaxSize(),
        )
    }

    @AssistedFactory
    @ContributesBinding(AppScope::class, binding<CountryDetailsComponent.Factory>())
    interface Factory : CountryDetailsComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            args: CountryDetailsArgs,
            navigateBack: DecomposeOnBackParameter,
        ): CountryDetailsComponentImpl
    }
}
