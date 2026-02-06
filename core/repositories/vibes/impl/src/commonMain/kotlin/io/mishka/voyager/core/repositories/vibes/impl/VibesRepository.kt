package io.mishka.voyager.core.repositories.vibes.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding
import io.github.jan.supabase.postgrest.Postgrest
import io.mishka.voyager.core.repositories.base.BaseRepository
import io.mishka.voyager.core.repositories.base.Syncable
import io.mishka.voyager.core.repositories.vibes.api.IVibesRepository
import io.mishka.voyager.core.repositories.vibes.api.datasource.VibeCategoryDao
import io.mishka.voyager.core.repositories.vibes.api.datasource.VibeDao
import io.mishka.voyager.core.repositories.vibes.api.datasource.VibesCountryDao
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeCategoryWithVibes
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeEntity
import io.mishka.voyager.core.repositories.vibes.api.models.remote.UserVibeDTO
import io.mishka.voyager.core.repositories.vibes.api.models.remote.VibeCategoryDTO
import io.mishka.voyager.core.repositories.vibes.api.models.remote.VibeDTO
import io.mishka.voyager.core.repositories.vibes.api.models.remote.VibesCountryDTO
import io.mishka.voyager.core.repositories.vibes.impl.mappers.toEntity
import io.mishka.voyager.supabase.api.ISupabaseAuth
import kotlinx.coroutines.flow.Flow

@ContributesBinding(
    scope = AppScope::class,
    binding = binding<IVibesRepository>(),
)
@ContributesIntoSet(
    scope = AppScope::class,
    binding = binding<Syncable>(),
)
class VibesRepository(
    private val vibeCategoryDao: Lazy<VibeCategoryDao>,
    private val vibeDao: Lazy<VibeDao>,
    private val vibesCountryDao: Lazy<VibesCountryDao>,
    private val supabasePostgrest: Postgrest,
    private val supabaseAuth: ISupabaseAuth,
) : IVibesRepository, BaseRepository() {

    override fun getVibesWithCategories(): Flow<List<VibeCategoryWithVibes>> {
        logger.d { "getVibesWithCategories()" }
        return vibeCategoryDao.value.getVibesWithCategoriesFlow()
    }

    override fun getVibesByCountryId(countryId: String): Flow<List<VibeEntity>> {
        logger.d { "getVibesByCountryId: countryId=$countryId" }
        return vibeDao.value.getVibesByCountryIdFlow(countryId)
    }

    override suspend fun addUserVibes(vibeIds: List<String>) {
        logger.d { "addUserVibes: vibeIds=$vibeIds" }

        retryAction {
            supabaseAuth.getAsyncCurrentUser()?.id?.let { userId ->
                // Replace all current user vibes
                supabasePostgrest.from(TABLE_VIBES_USER).delete {
                    filter {
                        UserVibeDTO::userId eq userId
                    }
                }

                supabasePostgrest.from(TABLE_VIBES_USER).insert(
                    vibeIds.map { vibeId ->
                        UserVibeDTO(
                            userId = userId,
                            vibeId = vibeId,
                        )
                    }
                )
            }
        }
    }

    override suspend fun sync() {
        logger.i { "Sync vibes repository" }

        retryAction {
            val categories = supabasePostgrest.from(TABLE_VIBE_CATEGORIES)
                .select()
                .decodeList<VibeCategoryDTO>()
                .map { dto -> dto.toEntity() }

            val vibes = supabasePostgrest.from(TABLE_VIBES)
                .select()
                .decodeList<VibeDTO>()
                .map { dto -> dto.toEntity() }

            val vibesCountry = supabasePostgrest.from(TABLE_VIBES_COUNTRY)
                .select()
                .decodeList<VibesCountryDTO>()
                .map { dto -> dto.toEntity() }

            vibeCategoryDao.value.replaceAll(*categories.toTypedArray())
            vibeDao.value.replaceAll(*vibes.toTypedArray())
            vibesCountryDao.value.replaceAll(vibesCountry)
        }
    }

    private companion object {
        const val TABLE_VIBE_CATEGORIES = "vibe_categories"
        const val TABLE_VIBES = "vibes"
        const val TABLE_VIBES_USER = "vibes_user"
        const val TABLE_VIBES_COUNTRY = "vibes_country"
    }
}
