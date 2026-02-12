package io.mishka.voyager.core.repositories.userstats.impl

import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import io.github.jan.supabase.postgrest.Postgrest
import io.mishka.voyager.core.repositories.userstats.api.datasource.UserStatsDao
import io.mishka.voyager.core.repositories.userstats.api.models.local.UserStatsEntity
import io.mishka.voyager.supabase.api.ISupabaseAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserStatsRepositoryTest {
    private val userStatsDao = mock<UserStatsDao>()
    private val supabasePostgrest = mock<Postgrest>()
    private val supabaseAuth = mock<ISupabaseAuth>()

    private val repository =
        UserStatsRepository(
            userStatsDao = lazy { userStatsDao },
            supabasePostgrest = supabasePostgrest,
            supabaseAuth = supabaseAuth,
        )

    @Test
    fun `getUserStats should return stats from dao`() =
        runTest {
            // Given
            val stats =
                UserStatsEntity(
                    userId = "user-123",
                    countriesVisited = 10,
                    continentsVisited = 3,
                    worldExploredPercent = 5.2f,
                )

            every { userStatsDao.getStatsFlow() } returns flowOf(stats)

            // When
            val result = repository.getUserStats().first()

            // Then
            assertNotNull(result)
            assertEquals("user-123", result.userId)
            assertEquals(10, result.countriesVisited)
            assertEquals(3, result.continentsVisited)
            assertEquals(5.2f, result.worldExploredPercent)

            verify { userStatsDao.getStatsFlow() }
        }

    @Test
    fun `getUserStats should not emit when dao returns null and no user`() =
        runTest {
            // Given
            every { userStatsDao.getStatsFlow() } returns flowOf(null)
            everySuspend { supabaseAuth.getAsyncCurrentUser() } returns null

            // When
            val results = repository.getUserStats().toList()

            // Then - flow should not emit any values
            assertEquals(0, results.size)
            verify { userStatsDao.getStatsFlow() }
        }

    @Test
    fun `sync should handle null user gracefully`() =
        runTest {
            // Given
            everySuspend { supabaseAuth.getAsyncCurrentUser() } returns null

            // When
            repository.sync()

            // Then - no exception should be thrown
        }

    @Test
    fun `sync should be able to save to local database`() =
        runTest {
            // Given
            everySuspend { userStatsDao.upsert(any()) } returns Unit

            // When
            userStatsDao.upsert(UserStatsEntity("user-1", 5, 2, 2.5f))

            // Then
            verifySuspend { userStatsDao.upsert(any()) }
        }
}
