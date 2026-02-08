package io.mishka.voyager.details.impl.ui

import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import io.mishka.voyager.core.repositories.countries.api.ICountriesRepository
import io.mishka.voyager.core.repositories.countries.api.IUserCountriesRepository
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryWithVisitedStatus
import io.mishka.voyager.core.repositories.countrydetails.api.ICountryAiSuggestsRepository
import io.mishka.voyager.core.repositories.countrydetails.api.ICountryBestTimesRepository
import io.mishka.voyager.core.repositories.countrydetails.api.ICountryOverviewRepository
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryAiSuggestEntity
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryBestTimeEntity
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryOverviewEntity
import io.mishkav.voyager.core.ui.lifecycle.DecomposeViewModel
import io.mishkav.voyager.core.ui.uikit.resultflow.MutableUIResultFlow
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResultFlow
import io.mishkav.voyager.core.ui.uikit.resultflow.asUIResult
import io.mishkav.voyager.core.ui.uikit.resultflow.loadOrError
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@AssistedInject
class CountryDetailsViewModel(
    @Assisted private val countryId: String,
    private val countriesRepository: ICountriesRepository,
    private val countryAiSuggestRepository: ICountryAiSuggestsRepository,
    private val countryBestTimeRepository: ICountryBestTimesRepository,
    private val countryOverviewRepository: ICountryOverviewRepository,
    // TODO - Change on controller?
    // private val countryPodcastsRepository: ICountryPodcastsRepository,
    private val userCountriesRepository: IUserCountriesRepository,
) : DecomposeViewModel() {

    val countryState: StateFlow<UIResult<CountryWithVisitedStatus>> = countriesRepository
        .getCountryWithVisitedStatus(countryId)
        .asUIResult()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UIResult.Loading()
        )

    private val _aiSuggestsState = MutableUIResultFlow<List<CountryAiSuggestEntity>>()
    val aiSuggestsState: UIResultFlow<List<CountryAiSuggestEntity>> = _aiSuggestsState.asStateFlow()

    private val _bestTimesState = MutableUIResultFlow<List<CountryBestTimeEntity>>()
    val bestTimesState: UIResultFlow<List<CountryBestTimeEntity>> = _bestTimesState.asStateFlow()

    private val _overviewState = MutableUIResultFlow<CountryOverviewEntity?>()
    val overviewState: UIResultFlow<CountryOverviewEntity?> = _overviewState.asStateFlow()

    init {
        loadCountryDetails()
    }

    fun addOrRemoveVisitedCounty(isVisited: Boolean) {
        viewModelScope.launch {
            if (isVisited) {
                userCountriesRepository.addCountryToVisited(countryId)
            } else {
                userCountriesRepository.removeCountryFromVisited(countryId)
            }
        }
    }

    private fun loadCountryDetails() {
        viewModelScope.launch {
            listOf(
                async {
                    _aiSuggestsState.loadOrError {
                        countryAiSuggestRepository
                            .getByCountryId(countryId)
                            .getOrThrow()
                    }
                },
                async {
                    _bestTimesState.loadOrError {
                        countryBestTimeRepository
                            .getByCountryId(countryId)
                            .getOrThrow()
                    }
                },
                async {
                    _overviewState.loadOrError {
                        countryOverviewRepository
                            .getByCountryId(countryId)
                            .getOrThrow()
                    }
                }
            ).awaitAll()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(countryId: String): CountryDetailsViewModel
    }
}
