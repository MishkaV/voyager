package io.mishka.voyager.core.repositories.countrydetails.api.datasource

import androidx.room.Dao
import androidx.room.Query
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryOverviewEntity
import io.mishka.voyager.core.utils.room.BaseDao

@Dao
interface CountryOverviewDao : BaseDao<CountryOverviewEntity> {

    @Query("SELECT * FROM country_overview WHERE countryId = :countryId LIMIT 1")
    suspend fun getByCountryId(countryId: String): CountryOverviewEntity?
}
