package io.mishka.voyager.details.impl.api

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.binding
import io.mishka.voyager.details.api.CountryAiSuggestComponent
import io.mishka.voyager.details.api.models.CountryAiSuggestArgs
import io.mishka.voyager.details.impl.ui.details.utils.toComposeColor
import io.mishka.voyager.details.impl.ui.suggest.CountryAiSuggestScreen
import io.mishka.voyager.details.impl.ui.suggest.CountryAiSuggestViewModel
import io.mishkav.voyager.core.ui.lifecycle.viewModelWithFactory

@AssistedInject
class CountryAiSuggestComponentImpl(
    @Assisted componentContext: ComponentContext,
    @Assisted private val args: CountryAiSuggestArgs,
    private val countryAiSuggestViewModelProvider: Provider<CountryAiSuggestViewModel.Factory>,
) : CountryAiSuggestComponent(componentContext), ComponentContext by componentContext {

    @Composable
    override fun Render() {
        val viewModel = viewModelWithFactory {
            countryAiSuggestViewModelProvider().create(
                args = args,
            )
        }

        CountryAiSuggestScreen(
            viewModel = viewModel,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(args.backgroundHex.toComposeColor().copy(alpha = 1f)),
        )
    }

    @AssistedFactory
    @ContributesBinding(AppScope::class, binding<CountryAiSuggestComponent.Factory>())
    interface Factory : CountryAiSuggestComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            args: CountryAiSuggestArgs
        ): CountryAiSuggestComponentImpl
    }
}
