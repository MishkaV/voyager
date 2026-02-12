package io.mishka.voyager.core.repositories.userpreferences.impl

import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import io.github.jan.supabase.postgrest.Postgrest
import io.mishka.voyager.core.repositories.userpreferences.api.datasource.PrefDao
import io.mishka.voyager.core.repositories.userpreferences.api.models.local.PrefEntity
import io.mishka.voyager.supabase.api.ISupabaseAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import dev.mokkery.matcher.any as anyMatcher

class UserPreferencesRepositoryTest {
    private val prefDao = mock<PrefDao>()
    private val supabasePostgrest = mock<Postgrest>()
    private val supabaseAuth = mock<ISupabaseAuth>()

    private val repository =
        UserPreferencesRepository(
            prefDao = lazy { prefDao },
            supabasePostgrest = supabasePostgrest,
            supabaseAuth = supabaseAuth,
        )

    @Test
    fun `getAllPrefs should return all preferences from dao`() =
        runTest {
            // Given
            val prefs =
                listOf(
                    PrefEntity(id = "pref-1", title = "Budget Friendly"),
                    PrefEntity(id = "pref-2", title = "Luxury"),
                )

            every { prefDao.getAllPrefsFlow() } returns flowOf(prefs)

            // When
            val result = repository.getAllPrefs().first()

            // Then
            assertNotNull(result)
            assertEquals(2, result.size)
            assertEquals("Budget Friendly", result[0].title)
            assertEquals("Luxury", result[1].title)

            verify { prefDao.getAllPrefsFlow() }
        }

    @Test
    fun `sync should be able to save to local database`() =
        runTest {
            // Given
            val pref1 = PrefEntity(id = "pref-1", title = "Budget Friendly")
            val pref2 = PrefEntity(id = "pref-2", title = "Luxury")
            everySuspend { prefDao.replaceAll(anyMatcher(), anyMatcher()) } returns Unit

            // When
            prefDao.replaceAll(pref1, pref2)

            // Then
            verifySuspend { prefDao.replaceAll(anyMatcher(), anyMatcher()) }
        }
}
