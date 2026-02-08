package io.mishka.voyager.core.repositories.countrydetails.api

import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryBestTimeEntity

interface ICountryBestTimesRepository {

    suspend fun getByCountryId(countryId: String, forceUpdate: Boolean = false): Result<List<CountryBestTimeEntity>>
}
