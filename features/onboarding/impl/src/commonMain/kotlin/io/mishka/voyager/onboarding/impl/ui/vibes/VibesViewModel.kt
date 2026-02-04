package io.mishka.voyager.onboarding.impl.ui.vibes

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateSetOf
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import dev.zacsweers.metro.Inject
import io.mishka.voyager.core.repositories.vibes.api.IVibesRepository
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeCategoryWithVibes
import io.mishka.voyager.core.storage.settings.VoyagerSettingsKeys
import io.mishkav.voyager.core.ui.lifecycle.DecomposeViewModel
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import io.mishkav.voyager.core.ui.uikit.resultflow.asUIResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalSettingsApi::class)
@Inject
class VibesViewModel(
    private val vibesRepository: IVibesRepository,
    private val settings: SuspendSettings,
) : DecomposeViewModel() {

    val selectedVibeIds = mutableStateSetOf<String>()

    val vibesState: StateFlow<VibesUIState> = vibesUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = VibesUIState.Loading,
        )

    fun addSelectedVibes() {
        viewModelScope.launch {
            vibesRepository.addUserVibes(selectedVibeIds.toList())
        }
    }

    fun setOnboardingAsCompleted() {
        viewModelScope.launch {
            settings.putBoolean(VoyagerSettingsKeys.IS_ONBOARDING_VIEWED, true)
        }
    }

    private fun vibesUiState(): Flow<VibesUIState> {
        return vibesRepository.getVibesWithCategories()
            .asUIResult()
            .map { state ->
                when (state) {
                    is UIResult.Error -> VibesUIState.Error(state.exception)
                    is UIResult.Loading -> VibesUIState.Loading
                    is UIResult.Success -> VibesUIState.Success(state.data)
                }
            }
    }
}

@Immutable
sealed interface VibesUIState {
    data object Loading : VibesUIState
    data class Success(val vibes: List<VibeCategoryWithVibes>) : VibesUIState
    data class Error(val exception: Throwable) : VibesUIState
}
