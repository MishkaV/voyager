package io.mishka.voyager.details.impl.ui.details

import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import io.mishka.voyager.common.audiocontroller.api.IAudioController
import io.mishka.voyager.common.audiocontroller.api.models.PlaybackState
import io.mishka.voyager.common.audiocontroller.api.models.PodcastPlaybackInfo
import io.mishka.voyager.core.repositories.countries.api.ICountriesRepository
import io.mishka.voyager.core.repositories.countries.api.IUserCountriesRepository
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryEntity
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryWithVisitedStatus
import io.mishka.voyager.core.repositories.countrydetails.api.ICountryAiSuggestsRepository
import io.mishka.voyager.core.repositories.countrydetails.api.ICountryBestTimesRepository
import io.mishka.voyager.core.repositories.countrydetails.api.ICountryOverviewRepository
import io.mishka.voyager.core.repositories.countrydetails.api.ICountryPodcastsRepository
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryPodcastEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountryDetailsViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private val audioController = mock<IAudioController>()
    private val countriesRepository = mock<ICountriesRepository>()
    private val countryAiSuggestRepository = mock<ICountryAiSuggestsRepository>()
    private val countryBestTimeRepository = mock<ICountryBestTimesRepository>()
    private val countryOverviewRepository = mock<ICountryOverviewRepository>()
    private val countryPodcastsRepository = mock<ICountryPodcastsRepository>()
    private val userCountriesRepository = mock<IUserCountriesRepository>()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Setup default mock behaviors
        every { audioController.playbackState } returns MutableStateFlow(PlaybackState.IDLE)
        every { audioController.playbackInfo } returns MutableStateFlow(null)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load country details`() =
        runTest {
            // Given
            val countryId = "country-1"
            val country = createTestCountry(countryId)

            every { countriesRepository.getCountryWithVisitedStatus(countryId) } returns flowOf(country)
            everySuspend { countryAiSuggestRepository.getByCountryId(countryId) } returns Result.success(emptyList())
            everySuspend { countryBestTimeRepository.getByCountryId(countryId) } returns Result.success(emptyList())
            everySuspend { countryOverviewRepository.getByCountryId(countryId) } returns Result.success(null)
            everySuspend { countryPodcastsRepository.getByCountryId(countryId) } returns Result.success(null)

            // When
            val viewModel = createViewModel(countryId)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            verifySuspend { countryAiSuggestRepository.getByCountryId(countryId) }
            verifySuspend { countryBestTimeRepository.getByCountryId(countryId) }
            verifySuspend { countryOverviewRepository.getByCountryId(countryId) }
            verifySuspend { countryPodcastsRepository.getByCountryId(countryId) }
        }

    @Test
    fun `addOrRemoveVisitedCounty should add country when isVisited is true`() =
        runTest {
            // Given
            val countryId = "country-1"
            setupDefaultMocks(countryId)
            everySuspend { userCountriesRepository.addCountryToVisited(countryId) } returns Unit

            // When
            val viewModel = createViewModel(countryId)
            viewModel.addOrRemoveVisitedCounty(isVisited = true)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            verifySuspend { userCountriesRepository.addCountryToVisited(countryId) }
        }

    @Test
    fun `addOrRemoveVisitedCounty should remove country when isVisited is false`() =
        runTest {
            // Given
            val countryId = "country-1"
            setupDefaultMocks(countryId)
            everySuspend { userCountriesRepository.removeCountryFromVisited(countryId) } returns Unit

            // When
            val viewModel = createViewModel(countryId)
            viewModel.addOrRemoveVisitedCounty(isVisited = false)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            verifySuspend { userCountriesRepository.removeCountryFromVisited(countryId) }
        }

    @Test
    fun `playPodcast should load and play new podcast`() =
        runTest {
            // Given
            val countryId = "country-1"
            val podcast =
                CountryPodcastEntity(
                    id = "podcast-1",
                    countryId = countryId,
                    title = "Test Podcast",
                    subtitle = "Subtitle",
                    durationSec = 300,
                    audioFullPatch = "/audio/test.mp3",
                )

            setupDefaultMocks(countryId)
            everySuspend {
                audioController.loadAndPlay(
                    countryId = countryId,
                    podcastId = podcast.id,
                    audioFullPath = podcast.audioFullPatch,
                    title = podcast.title,
                    subtitle = podcast.subtitle,
                    durationSec = podcast.durationSec,
                )
            } returns Unit

            // When
            val viewModel = createViewModel(countryId)
            viewModel.playPodcast(podcast)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            verifySuspend {
                audioController.loadAndPlay(
                    countryId = countryId,
                    podcastId = podcast.id,
                    audioFullPath = podcast.audioFullPatch,
                    title = podcast.title,
                    subtitle = podcast.subtitle,
                    durationSec = podcast.durationSec,
                )
            }
        }

    @Test
    fun `playPodcast should resume current podcast if same podcast`() =
        runTest {
            // Given
            val countryId = "country-1"
            val podcastId = "podcast-1"
            val podcast =
                CountryPodcastEntity(
                    id = podcastId,
                    countryId = countryId,
                    title = "Test Podcast",
                    subtitle = "Subtitle",
                    durationSec = 300,
                    audioFullPatch = "/audio/test.mp3",
                )

            val playbackInfo =
                PodcastPlaybackInfo(
                    countryId = countryId,
                    podcastId = podcastId,
                    audioFullPath = "/audio/test.mp3",
                    title = "Test Podcast",
                    subtitle = "Subtitle",
                    durationSec = 300,
                    currentPositionSec = 100,
                    playbackState = PlaybackState.PLAYING,
                )

            setupDefaultMocks(countryId)
            every { audioController.playbackInfo } returns MutableStateFlow(playbackInfo)
            everySuspend { audioController.play() } returns Unit

            // When
            val viewModel = createViewModel(countryId)
            viewModel.playPodcast(podcast)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            verifySuspend { audioController.play() }
        }

    @Test
    fun `seekTo should delegate to audio controller`() =
        runTest {
            // Given
            val countryId = "country-1"
            val positionSec = 150

            setupDefaultMocks(countryId)
            everySuspend { audioController.seekTo(positionSec) } returns Unit

            // When
            val viewModel = createViewModel(countryId)
            viewModel.seekTo(positionSec)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            verifySuspend { audioController.seekTo(positionSec) }
        }

    @Test
    fun `pausePodcast should delegate to audio controller`() =
        runTest {
            // Given
            val countryId = "country-1"

            setupDefaultMocks(countryId)
            everySuspend { audioController.pause() } returns Unit

            // When
            val viewModel = createViewModel(countryId)
            viewModel.pausePodcast()
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            verifySuspend { audioController.pause() }
        }

    private fun createViewModel(countryId: String): CountryDetailsViewModel =
        CountryDetailsViewModel(
            countryId = countryId,
            audioController = audioController,
            countriesRepository = countriesRepository,
            countryAiSuggestRepository = countryAiSuggestRepository,
            countryBestTimeRepository = countryBestTimeRepository,
            countryOverviewRepository = countryOverviewRepository,
            countryPodcastsRepository = countryPodcastsRepository,
            userCountriesRepository = userCountriesRepository,
        )

    private fun setupDefaultMocks(countryId: String) {
        val country = createTestCountry(countryId)

        every { countriesRepository.getCountryWithVisitedStatus(countryId) } returns flowOf(country)
        everySuspend { countryAiSuggestRepository.getByCountryId(countryId) } returns Result.success(emptyList())
        everySuspend { countryBestTimeRepository.getByCountryId(countryId) } returns Result.success(emptyList())
        everySuspend { countryOverviewRepository.getByCountryId(countryId) } returns Result.success(null)
        everySuspend { countryPodcastsRepository.getByCountryId(countryId) } returns Result.success(null)
    }

    private fun createTestCountry(countryId: String): CountryWithVisitedStatus =
        CountryWithVisitedStatus(
            country =
            CountryEntity(
                id = countryId,
                iso2 = "US",
                name = "Test Country",
                capital = "Test Capital",
                continent = "Test Continent",
                primaryLanguage = "English",
                primaryLanguageCode = "en",
                primaryCurrency = "Dollar",
                primaryCurrencyCode = "USD",
                flagFullPatch = "/flags/test.png",
                backgroundHex = "#FFFFFF",
            ),
            isVisited = false,
        )
}
