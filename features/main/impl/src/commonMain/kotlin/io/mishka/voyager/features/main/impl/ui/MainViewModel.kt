package io.mishka.voyager.features.main.impl.ui

import co.touchlab.kermit.Logger
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import io.mishka.voyager.core.repositories.countries.api.ICountryGeocodingRepository
import io.mishka.voyager.core.repositories.countries.api.IUserCountriesRepository
import io.mishkav.voyager.core.ui.lifecycle.DecomposeViewModel
import io.mishkav.voyager.core.utils.permissions.impl.location.ILocationController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@AssistedInject
class MainViewModel(
    @Assisted private val locationController: ILocationController,
    private val countryGeocodingRepository: ICountryGeocodingRepository,
    private val userCountriesRepository: IUserCountriesRepository,
) : DecomposeViewModel() {

    private val _autodetectState = MutableStateFlow<AutodetectState>(AutodetectState.Idle)
    val autodetectState: StateFlow<AutodetectState> = _autodetectState.asStateFlow()

    val activeCountry = countryGeocodingRepository.activeCountry

    suspend fun detectCurrentCountry() {
        try {
            _autodetectState.value = AutodetectState.Loading

            val coordinates = locationController.getCurrentLocation()

            if (coordinates == null) {
                _autodetectState.value = AutodetectState.Error("Failed to get location")
                Logger.e { "Failed to get location" }
                return
            }

            countryGeocodingRepository.searchCountryByCoordinates(
                latitude = coordinates.latitude,
                longitude = coordinates.longitude
            )

            _autodetectState.value = AutodetectState.Success
        } catch (e: Exception) {
            Logger.e(e) { "Failed to detect current country" }
            _autodetectState.value = AutodetectState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun addCountryToVisited(countryId: String) {
        userCountriesRepository.addCountryToVisited(countryId)
    }

    fun resetAutodetectState() {
        _autodetectState.value = AutodetectState.Idle
    }

    @AssistedFactory
    interface Factory {
        fun create(locationController: ILocationController): MainViewModel
    }
}

sealed interface AutodetectState {
    data object Idle : AutodetectState
    data object Loading : AutodetectState
    data object Success : AutodetectState
    data class Error(val message: String) : AutodetectState
}
