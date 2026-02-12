package io.mishka.voyager.core.repositories.countries.impl

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import io.github.jan.supabase.postgrest.Postgrest
import io.mishka.voyager.core.repositories.countries.api.datasource.UserCountryDao
import io.mishka.voyager.core.repositories.userstats.api.IUserStatsRepository
import io.mishka.voyager.supabase.api.ISupabaseAuth
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class UserCountriesRepositoryTest {
    private val userCountryDao = mock<UserCountryDao>()
    private val supabasePostgrest = mock<Postgrest>()
    private val supabaseAuth = mock<ISupabaseAuth>()
    private val userStatsRepository = mock<IUserStatsRepository>()

    private val repository =
        UserCountriesRepository(
            userCountryDao = lazy { userCountryDao },
            supabasePostgrest = supabasePostgrest,
            supabaseAuth = supabaseAuth,
            userStatsRepository = userStatsRepository,
        )

    @Test
    fun `addCountryToVisited should call userStatsRepository sync when user is null`() =
        runTest {
            // Given
            val countryId = "country-1"

            // Return null user - user not logged in
            everySuspend { supabaseAuth.getAsyncCurrentUser() } returns null
            everySuspend { userStatsRepository.sync() } returns Unit

            // When
            repository.addCountryToVisited(countryId)

            // Then
            verifySuspend { userStatsRepository.sync() }
        }

    @Test
    fun `removeCountryFromVisited should call userStatsRepository sync when user is null`() =
        runTest {
            // Given
            val countryId = "country-1"

            // Return null user - user not logged in
            everySuspend { supabaseAuth.getAsyncCurrentUser() } returns null
            everySuspend { userStatsRepository.sync() } returns Unit

            // When
            repository.removeCountryFromVisited(countryId)

            // Then
            verifySuspend { userStatsRepository.sync() }
        }

    @Test
    fun `cleanup should delete all user countries from local database`() =
        runTest {
            // Given
            everySuspend { userCountryDao.deleteAll() } returns Unit

            // When
            repository.cleanup()

            // Then
            verifySuspend { userCountryDao.deleteAll() }
        }
}
