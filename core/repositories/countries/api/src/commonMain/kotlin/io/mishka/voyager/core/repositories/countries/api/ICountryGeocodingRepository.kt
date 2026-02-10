package io.mishka.voyager.core.repositories.countries.api

import io.mishka.voyager.core.repositories.countries.api.models.local.CountryWithVisitedStatus
import kotlinx.coroutines.flow.Flow

interface ICountryGeocodingRepository {

    val activeCountry: Flow<CountryWithVisitedStatus?>

    /**
     * Get country information by coordinates using reverse geocoding
     * Returns a Flow that will emit updates when country's visited status changes
     * @param latitude Latitude coordinate
     * @param longitude Longitude coordinate
     * @return Flow of CountryWithVisitedStatus or null if no country found at these coordinates
     */
    suspend fun searchCountryByCoordinates(
        latitude: Double,
        longitude: Double
    )
}
