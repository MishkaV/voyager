package io.mishka.voyager.core.repositories.vibes.api.datasource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibesCountryEntity
import io.mishka.voyager.core.utils.room.BaseDao

@Dao
interface VibesCountryDao : BaseDao<VibesCountryEntity> {

    @Query("SELECT * FROM vibes_country")
    suspend fun getAll(): List<VibesCountryEntity>

    @Query("DELETE FROM vibes_country WHERE countryId = :countryId AND vibeId = :vibeId")
    suspend fun deleteEntry(countryId: String, vibeId: String)

    @Transaction
    suspend fun replaceAll(entities: List<VibesCountryEntity>) {
        val currentKeys = getAll().map { it.countryId to it.vibeId }.toSet()
        val newKeys = entities.map { it.countryId to it.vibeId }.toSet()
        val toDelete = currentKeys - newKeys
        toDelete.forEach { (countryId, vibeId) ->
            deleteEntry(countryId, vibeId)
        }
        upsert(*entities.toTypedArray())
    }
}
