package io.mishka.voyager.core.repositories.vibes.impl

import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import io.github.jan.supabase.postgrest.Postgrest
import io.mishka.voyager.core.repositories.vibes.api.datasource.VibeCategoryDao
import io.mishka.voyager.core.repositories.vibes.api.datasource.VibeDao
import io.mishka.voyager.core.repositories.vibes.api.datasource.VibesCountryDao
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeCategoryEntity
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeCategoryWithVibes
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeEntity
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibesCountryEntity
import io.mishka.voyager.supabase.api.ISupabaseAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import dev.mokkery.matcher.any as anyMatcher

class VibesRepositoryTest {
    private val vibeCategoryDao = mock<VibeCategoryDao>()
    private val vibeDao = mock<VibeDao>()
    private val vibesCountryDao = mock<VibesCountryDao>()
    private val supabasePostgrest = mock<Postgrest>()
    private val supabaseAuth = mock<ISupabaseAuth>()

    private val repository =
        VibesRepository(
            vibeCategoryDao = lazy { vibeCategoryDao },
            vibeDao = lazy { vibeDao },
            vibesCountryDao = lazy { vibesCountryDao },
            supabasePostgrest = supabasePostgrest,
            supabaseAuth = supabaseAuth,
        )

    @Test
    fun `getVibesWithCategories should return vibes with categories from dao`() =
        runTest {
            // Given
            val category = VibeCategoryEntity(id = "cat-1", title = "Adventure")
            val vibe = VibeEntity(id = "vibe-1", categoryId = "cat-1", title = "Hiking", iconEmoji = "🥾")
            val vibesWithCategories =
                listOf(
                    VibeCategoryWithVibes(category = category, vibes = listOf(vibe)),
                )

            every { vibeCategoryDao.getVibesWithCategoriesFlow() } returns flowOf(vibesWithCategories)

            // When
            val result = repository.getVibesWithCategories().first()

            // Then
            assertNotNull(result)
            assertEquals(1, result.size)
            assertEquals("Adventure", result[0].category.title)
            assertEquals(1, result[0].vibes.size)
            assertEquals("Hiking", result[0].vibes[0].title)

            verify { vibeCategoryDao.getVibesWithCategoriesFlow() }
        }

    @Test
    fun `getVibesByCountryId should return vibes for specific country`() =
        runTest {
            // Given
            val countryId = "country-1"
            val vibes =
                listOf(
                    VibeEntity(id = "vibe-1", categoryId = "cat-1", title = "Beach", iconEmoji = "🏖️"),
                    VibeEntity(id = "vibe-2", categoryId = "cat-1", title = "Hiking", iconEmoji = "🥾"),
                )

            every { vibeDao.getVibesByCountryIdFlow(countryId) } returns flowOf(vibes)

            // When
            val result = repository.getVibesByCountryId(countryId).first()

            // Then
            assertNotNull(result)
            assertEquals(2, result.size)
            assertEquals("Beach", result[0].title)
            assertEquals("Hiking", result[1].title)

            verify { vibeDao.getVibesByCountryIdFlow(countryId) }
        }

    @Test
    fun `addUserVibes should handle null user gracefully`() =
        runTest {
            // Given
            val vibeIds = listOf("vibe-1", "vibe-2")
            everySuspend { supabaseAuth.getAsyncCurrentUser() } returns null

            // When
            repository.addUserVibes(vibeIds)

            // Then - no exception should be thrown
        }

    @Test
    fun `sync should be able to save vibes to local database`() =
        runTest {
            // Given
            val category1 = VibeCategoryEntity(id = "cat-1", title = "Adventure")
            val category2 = VibeCategoryEntity(id = "cat-2", title = "Culture")
            val vibe1 = VibeEntity(id = "vibe-1", categoryId = "cat-1", title = "Hiking", iconEmoji = "🥾")
            val vibe2 = VibeEntity(id = "vibe-2", categoryId = "cat-2", title = "Museums", iconEmoji = "🏛️")
            val vibesCountries = listOf<VibesCountryEntity>()

            everySuspend { vibeCategoryDao.replaceAll(anyMatcher(), anyMatcher()) } returns Unit
            everySuspend { vibeDao.replaceAll(anyMatcher(), anyMatcher()) } returns Unit
            everySuspend { vibesCountryDao.replaceAll(anyMatcher()) } returns Unit

            // When
            vibeCategoryDao.replaceAll(category1, category2)
            vibeDao.replaceAll(vibe1, vibe2)
            vibesCountryDao.replaceAll(vibesCountries)

            // Then
            verifySuspend { vibeCategoryDao.replaceAll(anyMatcher(), anyMatcher()) }
            verifySuspend { vibeDao.replaceAll(anyMatcher(), anyMatcher()) }
            verifySuspend { vibesCountryDao.replaceAll(anyMatcher()) }
        }
}
