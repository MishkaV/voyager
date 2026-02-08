package io.mishka.voyager.core.repositories.countrydetails.api.datasource

import androidx.room.Dao
import androidx.room.Query
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryPodcastEntity
import io.mishka.voyager.core.utils.room.BaseDao

@Dao
interface CountryPodcastDao : BaseDao<CountryPodcastEntity> {

    @Query("SELECT COUNT(*) FROM country_podcasts WHERE countryId = :countryId")
    suspend fun getCountByCountryId(countryId: String): Int

    @Query("SELECT * FROM country_podcasts WHERE countryId = :countryId")
    suspend fun getByCountryId(countryId: String): List<CountryPodcastEntity>
}
