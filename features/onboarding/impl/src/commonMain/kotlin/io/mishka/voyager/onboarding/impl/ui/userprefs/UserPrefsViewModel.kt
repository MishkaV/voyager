package io.mishka.voyager.onboarding.impl.ui.userprefs

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import dev.zacsweers.metro.Inject
import io.mishka.voyager.core.repositories.userpreferences.api.IUserPreferencesRepository
import io.mishka.voyager.core.repositories.userpreferences.api.models.local.PrefEntity
import io.mishkav.voyager.core.ui.lifecycle.DecomposeViewModel
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import io.mishkav.voyager.core.ui.uikit.resultflow.asUIResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Inject
class UserPrefsViewModel(
    private val userPreferencesRepository: IUserPreferencesRepository,
) : DecomposeViewModel() {

    val selectedPrefsIds = mutableStateListOf<String>()

    val prefsState: StateFlow<PrefsUIState> = prefsUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PrefsUIState.Loading,
        )

    fun addUserPref(prefIds: List<String>) {
        viewModelScope.launch {
            userPreferencesRepository.addUserPrefs(prefIds)
        }
    }

    private fun prefsUiState(): Flow<PrefsUIState> {
        return userPreferencesRepository.getAllPrefs()
            .asUIResult()
            .map { state ->
                when (state) {
                    is UIResult.Error -> PrefsUIState.Error(state.exception)
                    is UIResult.Loading, is UIResult.Nothing -> PrefsUIState.Loading
                    is UIResult.Success -> PrefsUIState.Success(state.data)
                }
            }
    }
}

@Immutable
sealed interface PrefsUIState {
    data object Loading : PrefsUIState
    data class Success(val prefs: List<PrefEntity>) : PrefsUIState
    data class Error(val exception: Throwable) : PrefsUIState
}
