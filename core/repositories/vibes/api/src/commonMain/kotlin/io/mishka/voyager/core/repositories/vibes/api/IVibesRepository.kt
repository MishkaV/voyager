package io.mishka.voyager.core.repositories.vibes.api

import io.mishka.voyager.core.repositories.base.Syncable
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeCategoryWithVibes
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeEntity
import kotlinx.coroutines.flow.Flow

interface IVibesRepository : Syncable {

    fun getVibesWithCategories(): Flow<List<VibeCategoryWithVibes>>

    suspend fun addUserVibes(vibeIds: List<String>)

    fun getVibesByCountryId(countryId: String): Flow<List<VibeEntity>>
}
