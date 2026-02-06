package io.mishka.voyager.search.impl.api

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.binding
import io.mishka.voyager.search.api.SearchComponent
import io.mishka.voyager.search.impl.ui.SearchScreen
import io.mishka.voyager.search.impl.ui.SearchViewModel
import io.mishkav.voyager.core.ui.lifecycle.viewModelWithFactory

@AssistedInject
class SearchComponentImpl(
    @Assisted componentContext: ComponentContext,
    private val searchViewModelProvider: Provider<SearchViewModel>,
) : SearchComponent(componentContext), ComponentContext by componentContext {

    @Composable
    override fun Render() {
        val viewModel = viewModelWithFactory {
            searchViewModelProvider()
        }

        SearchScreen(
            backHandler = backHandler,
            viewModel = viewModel,
        )
    }

    @AssistedFactory
    @ContributesBinding(AppScope::class, binding<SearchComponent.Factory>())
    interface Factory : SearchComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
        ): SearchComponentImpl
    }
}
