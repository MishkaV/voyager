package io.mishka.voyager.core.repositories.vibes.api.datasource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeEntity
import io.mishka.voyager.core.utils.room.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface VibeDao : BaseDao<VibeEntity> {

    @Query(
        """
        SELECT vibes.* FROM vibes
        INNER JOIN vibes_country ON vibes.id = vibes_country.vibeId
        WHERE vibes_country.countryId = :countryId
    """
    )
    fun getVibesByCountryIdFlow(countryId: String): Flow<List<VibeEntity>>

    @Query("DELETE FROM vibes WHERE id NOT IN (:ids)")
    suspend fun deleteNotIn(ids: List<String>)

    @Transaction
    suspend fun replaceAll(vararg obj: VibeEntity) {
        deleteNotIn(obj.map { it.id })
        upsert(*obj)
    }
}
