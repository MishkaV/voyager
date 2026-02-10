package io.mishka.voyager.core.repositories.countries.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.binding
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import io.mishka.voyager.core.repositories.base.BaseRepository
import io.mishka.voyager.core.repositories.countries.api.ICountryGeocodingRepository
import io.mishka.voyager.core.repositories.countries.api.datasource.CountryDao
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryWithVisitedStatus
import io.mishka.voyager.core.repositories.countries.api.models.remote.NominatimResponseDTO
import io.mishka.voyager.supabase.api.ISupabaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

@ContributesBinding(
    scope = AppScope::class,
    binding = binding<ICountryGeocodingRepository>(),
)
class CountryGeocodingRepository(
    private val httpClient: Lazy<HttpClient>,
    private val countryDao: Lazy<CountryDao>,
    private val supabaseAuth: ISupabaseAuth,
) : ICountryGeocodingRepository, BaseRepository() {

    private val _selectedCountryCode = MutableStateFlow<String?>(null)

    override val activeCountry: Flow<CountryWithVisitedStatus?> =
        combine(
            _selectedCountryCode,
            supabaseAuth.userFlow.filterNotNull()
        ) { countryCode, userInfo ->
            countryCode to userInfo
        }.flatMapLatest { (countryCode, userInfo) ->
            if (countryCode == null) {
                flowOf(null)
            } else {
                countryDao.value.getCountryByCode(
                    userId = userInfo.id,
                    countryCode = countryCode
                ).map { countryWithVisitedStatusRoom ->
                    countryWithVisitedStatusRoom?.toDomainModel()
                }
            }
        }

    override suspend fun searchCountryByCoordinates(
        latitude: Double,
        longitude: Double
    ) {
        logger.d { "getCountryByCoordinates: lat=$latitude, lon=$longitude" }

        // Call Nominatim API to get country code from coordinates
        val countryCodeResult = retryAction {
            val response = httpClient.value.get(NOMINATIM_REVERSE_GEOCODING_URL) {
                parameter("format", "json")
                parameter("lat", latitude)
                parameter("lon", longitude)
                parameter("zoom", 3) // Country level zoom
                header(HttpHeaders.UserAgent, "Voyager/1.0") // Nominatim requires User-Agent
            }.body<NominatimResponseDTO>()

            logger.d { "Nominatim response: country=${response.address?.country}" }

            response.address?.countryCode
        }

        // Update selected country code, which will trigger activeCountry Flow update
        _selectedCountryCode.value = countryCodeResult.getOrNull()

        if (_selectedCountryCode.value == null) {
            logger.w { "No country code found for coordinates: lat=$latitude, lon=$longitude" }
        }
    }

    private companion object {
        const val NOMINATIM_REVERSE_GEOCODING_URL = "https://nominatim.openstreetmap.org/reverse"
    }
}
