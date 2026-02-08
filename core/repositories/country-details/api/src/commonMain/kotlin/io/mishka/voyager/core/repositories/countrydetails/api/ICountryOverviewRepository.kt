package io.mishka.voyager.core.repositories.countrydetails.api

import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryOverviewEntity

interface ICountryOverviewRepository {

    suspend fun getByCountryId(countryId: String, forceUpdate: Boolean = false): Result<CountryOverviewEntity?>
}
