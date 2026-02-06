package io.mishka.voyager.search.impl.ui

import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.zacsweers.metro.Inject
import io.mishka.voyager.core.repositories.countries.api.ICountriesRepository
import io.mishka.voyager.core.repositories.countries.api.IUserCountriesRepository
import io.mishka.voyager.core.repositories.countries.api.models.local.Continent
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryWithVisitedStatus
import io.mishkav.voyager.core.ui.lifecycle.DecomposeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Inject
class SearchViewModel(
    private val countriesRepository: ICountriesRepository,
    private val userCountriesRepository: IUserCountriesRepository,
) : DecomposeViewModel() {

    private val selectedQuery = MutableStateFlow("")

    private val _selectedContinent = MutableStateFlow<Continent?>(null)
    val selectedContinent = _selectedContinent.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val countriesState: Flow<PagingData<CountryWithVisitedStatus>> =
        combine(
            flow = selectedQuery
                .debounce(QUERY_DEBOUNCE_MILLS)
                .map { it.trim() }
                .distinctUntilChanged(),
            flow2 = _selectedContinent
        ) { query, continent -> query to continent }
            .distinctUntilChanged()
            .flatMapLatest { (query, continent) ->
                countriesRepository.getCountriesWithVisitedStatus(
                    query = query,
                    continent = continent,
                )
            }
            .cachedIn(viewModelScope)

    fun updateQuery(newQuery: String) {
        selectedQuery.value = newQuery
    }

    fun selectContinent(continent: Continent?) {
        _selectedContinent.value = continent
    }

    fun addOrRemoveVisitedCounty(
        countryId: String,
        isVisited: Boolean,
    ) {
        viewModelScope.launch {
            if (isVisited) {
                userCountriesRepository.addCountryToVisited(countryId)
            } else {
                userCountriesRepository.removeCountryFromVisited(countryId)
            }
        }
    }

    private companion object {
        const val QUERY_DEBOUNCE_MILLS = 200L
    }
}
