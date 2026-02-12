package io.mishka.voyager.onboarding.impl.ui.vibes

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import io.mishka.voyager.core.repositories.vibes.api.IVibesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalSettingsApi::class)
class VibesViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private val vibesRepository = mock<IVibesRepository>()
    private val settings = mock<SuspendSettings>()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `vibesState should emit loading state initially`() =
        runTest {
            // Given
            every { vibesRepository.getVibesWithCategories() } returns flowOf(emptyList())

            // When
            val viewModel = createViewModel()
            // Don't advance the test dispatcher, check initial state

            // Then
            val state = viewModel.vibesState.value
            assertTrue(state is VibesUIState.Loading)
        }

    @Test
    fun `addSelectedVibes should handle empty selection`() =
        runTest {
            // Given
            every { vibesRepository.getVibesWithCategories() } returns flowOf(emptyList())
            everySuspend { vibesRepository.addUserVibes(emptyList()) } returns Unit

            // When
            val viewModel = createViewModel()
            viewModel.addSelectedVibes()
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            verifySuspend { vibesRepository.addUserVibes(emptyList()) }
        }

    @Test
    fun `setOnboardingAsCompleted should save to settings`() =
        runTest {
            // Given
            every { vibesRepository.getVibesWithCategories() } returns flowOf(emptyList())
            everySuspend { settings.putBoolean(any(), any()) } returns Unit

            // When
            val viewModel = createViewModel()
            viewModel.setOnboardingAsCompleted()
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            verifySuspend { settings.putBoolean(any(), true) }
        }

    @Test
    fun `selectedVibeIds should be mutable and allow adding vibes`() =
        runTest {
            // Given
            every { vibesRepository.getVibesWithCategories() } returns flowOf(emptyList())

            // When
            val viewModel = createViewModel()
            viewModel.selectedVibeIds.add("vibe-1")
            viewModel.selectedVibeIds.add("vibe-2")

            // Then
            assertEquals(2, viewModel.selectedVibeIds.size)
            assertTrue(viewModel.selectedVibeIds.contains("vibe-1"))
            assertTrue(viewModel.selectedVibeIds.contains("vibe-2"))
        }

    @Test
    fun `selectedVibeIds should allow removing vibes`() =
        runTest {
            // Given
            every { vibesRepository.getVibesWithCategories() } returns flowOf(emptyList())

            // When
            val viewModel = createViewModel()
            viewModel.selectedVibeIds.add("vibe-1")
            viewModel.selectedVibeIds.add("vibe-2")
            viewModel.selectedVibeIds.remove("vibe-1")

            // Then
            assertEquals(1, viewModel.selectedVibeIds.size)
            assertTrue(viewModel.selectedVibeIds.contains("vibe-2"))
        }

    private fun createViewModel(): VibesViewModel =
        VibesViewModel(
            vibesRepository = vibesRepository,
            settings = settings,
        )
}
