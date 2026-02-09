package io.mishka.voyager.details.impl.ui.suggest

import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import io.mishka.voyager.core.repositories.countrydetails.api.ICountryAiSuggestsRepository
import io.mishka.voyager.details.api.models.CountryAiSuggestArgs
import io.mishkav.voyager.core.ui.lifecycle.DecomposeViewModel
import io.mishkav.voyager.core.ui.uikit.resultflow.MutableUIResultFlow
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResultFlow
import io.mishkav.voyager.core.ui.uikit.resultflow.loadOrError
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@AssistedInject
class CountryAiSuggestViewModel(
    @Assisted private val args: CountryAiSuggestArgs,
    private val countryAiSuggestRepository: ICountryAiSuggestsRepository,
) : DecomposeViewModel() {

    private val _aiSuggestsState = MutableUIResultFlow<String>()
    val aiSuggestsState: UIResultFlow<String> = _aiSuggestsState.asStateFlow()

    init {
        viewModelScope.launch {
            _aiSuggestsState.loadOrError {
                countryAiSuggestRepository.generateAiSuggest(
                    aiSuggestId = args.aiSuggestId,
                    countryId = args.countryId,
                ).getOrThrow()
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            args: CountryAiSuggestArgs
        ): CountryAiSuggestViewModel
    }
}
