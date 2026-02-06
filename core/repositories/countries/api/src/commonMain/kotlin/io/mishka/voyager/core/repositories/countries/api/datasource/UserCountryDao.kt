package io.mishka.voyager.core.repositories.countries.api.datasource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.mishka.voyager.core.repositories.countries.api.models.local.UserCountryEntity
import io.mishka.voyager.core.utils.room.BaseDao

@Dao
interface UserCountryDao : BaseDao<UserCountryEntity> {

    @Query("SELECT * FROM countries_visited WHERE userId = :userId")
    suspend fun getByUserId(userId: String): List<UserCountryEntity>

    @Query("DELETE FROM countries_visited")
    suspend fun deleteAll()

    @Query("DELETE FROM countries_visited WHERE userId = :userId AND countryId = :countryId")
    suspend fun delete(userId: String, countryId: String)

    @Transaction
    suspend fun replaceAllForUser(userId: String, entities: List<UserCountryEntity>) {
        val currentIds = getByUserId(userId).map { it.countryId }.toSet()
        val newIds = entities.map { it.countryId }.toSet()
        val toDelete = currentIds - newIds
        toDelete.forEach { countryId ->
            delete(userId, countryId)
        }
        if (entities.isNotEmpty()) {
            upsert(*entities.toTypedArray())
        }
    }
}
