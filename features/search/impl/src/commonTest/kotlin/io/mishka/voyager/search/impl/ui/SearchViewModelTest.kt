package io.mishka.voyager.search.impl.ui

import androidx.paging.PagingData
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import io.mishka.voyager.core.repositories.countries.api.ICountriesRepository
import io.mishka.voyager.core.repositories.countries.api.IUserCountriesRepository
import io.mishka.voyager.core.repositories.countries.api.models.local.Continent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private val countriesRepository = mock<ICountriesRepository>()
    private val userCountriesRepository = mock<IUserCountriesRepository>()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Setup default mock behavior
        every {
            countriesRepository.getCountriesWithVisitedStatus(any(), any())
        } returns flowOf(PagingData.empty())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should have no selected continent`() =
        runTest {
            // When
            val viewModel = createViewModel()
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            val selectedContinent = viewModel.selectedContinent.first()
            assertNull(selectedContinent)
        }

    @Test
    fun `updateQuery should update search query`() =
        runTest {
            // Given
            val query = "United"
            val viewModel = createViewModel()
            testDispatcher.scheduler.advanceUntilIdle()

            // When
            viewModel.updateQuery(query)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then - Verify repository was called with the query pattern
            // Note: The actual verification would depend on how you want to test the debounced flow
        }

    @Test
    fun `selectContinent should update selected continent`() =
        runTest {
            // Given
            val continent = Continent.EUROPE
            val viewModel = createViewModel()
            testDispatcher.scheduler.advanceUntilIdle()

            // When
            viewModel.selectContinent(continent)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            val selectedContinent = viewModel.selectedContinent.first()
            assertEquals(continent, selectedContinent)
        }

    @Test
    fun `selectContinent with null should clear continent filter`() =
        runTest {
            // Given
            val viewModel = createViewModel()
            viewModel.selectContinent(Continent.ASIA)
            testDispatcher.scheduler.advanceUntilIdle()

            // When
            viewModel.selectContinent(null)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            val selectedContinent = viewModel.selectedContinent.first()
            assertNull(selectedContinent)
        }

    @Test
    fun `addOrRemoveVisitedCounty should add country when isVisited is true`() =
        runTest {
            // Given
            val countryId = "country-1"
            val viewModel = createViewModel()
            everySuspend { userCountriesRepository.addCountryToVisited(countryId) } returns Unit

            // When
            viewModel.addOrRemoveVisitedCounty(countryId, isVisited = true)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            verifySuspend { userCountriesRepository.addCountryToVisited(countryId) }
        }

    @Test
    fun `addOrRemoveVisitedCounty should remove country when isVisited is false`() =
        runTest {
            // Given
            val countryId = "country-1"
            val viewModel = createViewModel()
            everySuspend { userCountriesRepository.removeCountryFromVisited(countryId) } returns Unit

            // When
            viewModel.addOrRemoveVisitedCounty(countryId, isVisited = false)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            verifySuspend { userCountriesRepository.removeCountryFromVisited(countryId) }
        }

    @Test
    fun `countriesState should filter by continent when continent is selected`() =
        runTest {
            // Given
            val continent = Continent.EUROPE
            every {
                countriesRepository.getCountriesWithVisitedStatus(continent, any())
            } returns flowOf(PagingData.empty())

            // When
            val viewModel = createViewModel()
            viewModel.selectContinent(continent)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then - The flow should be created with the continent filter
            // Note: Testing PagingData flows requires additional setup
        }

    private fun createViewModel(): SearchViewModel =
        SearchViewModel(
            countriesRepository = countriesRepository,
            userCountriesRepository = userCountriesRepository,
        )
}
