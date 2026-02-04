package io.mishka.voyager.core.repositories.vibes.api.datasource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeCategoryEntity
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeCategoryWithVibes
import io.mishka.voyager.core.utils.room.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface VibeCategoryDao : BaseDao<VibeCategoryEntity> {

    @Transaction
    @Query("SELECT * FROM vibe_categories")
    fun getVibesWithCategoriesFlow(): Flow<List<VibeCategoryWithVibes>>

    @Query("DELETE FROM vibe_categories WHERE id NOT IN (:ids)")
    suspend fun deleteNotIn(ids: List<String>)

    @Transaction
    suspend fun replaceAll(vararg obj: VibeCategoryEntity) {
        deleteNotIn(obj.map { it.id })
        upsert(*obj)
    }
}
