package io.mishka.voyager.core.repositories.countries.api.datasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryEntity
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryWithVisitedStatusRoom
import io.mishka.voyager.core.utils.room.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao : BaseDao<CountryEntity> {

    @Query(
        """
        SELECT c.*,
               CASE WHEN cv.countryId IS NOT NULL THEN 1 ELSE 0 END as isVisited
        FROM countries c
        LEFT JOIN countries_visited cv ON c.id = cv.countryId AND cv.userId = :userId
        WHERE (:continent IS NULL OR c.continent = :continent)
        AND (:queryPattern IS NULL OR c.name LIKE :queryPattern)
        ORDER BY c.name ASC
    """
    )
    fun getPagingSourceWithVisitedStatus(
        userId: String,
        continent: String?,
        queryPattern: String?
    ): PagingSource<Int, CountryWithVisitedStatusRoom>

    @Query(
        """
        SELECT c.*,
               CASE WHEN cv.countryId IS NOT NULL THEN 1 ELSE 0 END as isVisited
        FROM countries c
        LEFT JOIN countries_visited cv ON c.id = cv.countryId AND cv.userId = :userId
        WHERE c.id = :countryId
    """
    )
    fun getCountryWithVisitedStatus(
        userId: String,
        countryId: String
    ): Flow<CountryWithVisitedStatusRoom?>

    @Query("DELETE FROM countries WHERE id NOT IN (:ids)")
    suspend fun deleteNotIn(ids: List<String>)

    @Transaction
    suspend fun replaceAll(vararg entities: CountryEntity) {
        deleteNotIn(entities.map { it.id })
        upsert(*entities)
    }
}
