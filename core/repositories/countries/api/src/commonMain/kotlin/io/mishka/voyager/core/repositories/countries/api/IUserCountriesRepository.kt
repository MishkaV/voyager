package io.mishka.voyager.core.repositories.countries.api

import io.mishka.voyager.core.repositories.base.Cleanable
import io.mishka.voyager.core.repositories.base.Syncable

interface IUserCountriesRepository : Syncable, Cleanable {

    suspend fun addCountryToVisited(countryId: String)

    suspend fun removeCountryFromVisited(countryId: String)
}
