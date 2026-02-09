package io.mishka.voyager.core.repositories.countries.api

import androidx.paging.PagingData
import io.mishka.voyager.core.repositories.base.Syncable
import io.mishka.voyager.core.repositories.countries.api.models.local.Continent
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryWithVisitedStatus
import kotlinx.coroutines.flow.Flow

interface ICountriesRepository : Syncable {

    fun getCountriesWithVisitedStatus(continent: Continent?, query: String?): Flow<PagingData<CountryWithVisitedStatus>>

    fun getCountryWithVisitedStatus(countryId: String): Flow<CountryWithVisitedStatus>
}
