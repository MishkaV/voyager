package io.mishka.voyager.core.repositories.countrydetails.api

import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryPodcastEntity

interface ICountryPodcastsRepository {

    suspend fun getByCountryId(countryId: String, forceUpdate: Boolean = false): Result<List<CountryPodcastEntity>>
}
