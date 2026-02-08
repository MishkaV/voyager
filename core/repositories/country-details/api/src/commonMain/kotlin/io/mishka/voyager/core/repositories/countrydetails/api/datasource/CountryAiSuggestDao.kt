package io.mishka.voyager.core.repositories.countrydetails.api.datasource

import androidx.room.Dao
import androidx.room.Query
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryAiSuggestEntity
import io.mishka.voyager.core.utils.room.BaseDao

@Dao
interface CountryAiSuggestDao : BaseDao<CountryAiSuggestEntity> {

    @Query("SELECT * FROM country_ai_suggests WHERE countryId = :countryId")
    suspend fun getByCountryId(countryId: String): List<CountryAiSuggestEntity>
}
