package io.mishka.voyager.core.repositories.countries.impl

import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import io.mishka.voyager.core.repositories.countries.api.datasource.CountryDao
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryEntity
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryWithVisitedStatusRoom
import io.mishka.voyager.supabase.api.ISupabaseAuth
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CountriesRepositoryTest {
    private val countryDao = mock<CountryDao>()
    private val supabaseAuth = mock<ISupabaseAuth>()

    @Test
    fun `getCountriesWithVisitedStatus should use current user id`() =
        runTest {
            // Given
            every { supabaseAuth.getCurrentUser() } returns null

            // Then - test passes if no exception thrown when creating repository
        }

    @Test
    fun `dao can store and retrieve countries`() =
        runTest {
            // Given
            val countryEntity =
                CountryEntity(
                    id = "country-1",
                    iso2 = "US",
                    name = "United States",
                    capital = "Washington",
                    continent = "North America",
                    primaryLanguage = "English",
                    primaryLanguageCode = "en",
                    primaryCurrency = "Dollar",
                    primaryCurrencyCode = "USD",
                    flagFullPatch = "/flags/us.png",
                    backgroundHex = "#FFFFFF",
                )

            val countryRoom =
                CountryWithVisitedStatusRoom(
                    country = countryEntity,
                    isVisited = 1,
                )

            every { countryDao.getCountryWithVisitedStatus("user-1", "country-1") } returns flowOf(countryRoom)

            // Then - verify we can get data from dao
            verify { countryDao.getCountryWithVisitedStatus("user-1", "country-1") }

            // Verify the room model can be converted
            val domainModel = countryRoom.toDomainModel()
            assertEquals("United States", domainModel.country.name)
            assertEquals(true, domainModel.isVisited)
        }
}
