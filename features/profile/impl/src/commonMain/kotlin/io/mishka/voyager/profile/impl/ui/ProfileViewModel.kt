package io.mishka.voyager.profile.impl.ui

import co.touchlab.kermit.Logger
import dev.zacsweers.metro.Inject
import io.github.jan.supabase.auth.Auth
import io.mishka.voyager.core.repositories.userstats.api.IUserStatsRepository
import io.mishka.voyager.core.repositories.userstats.api.models.local.UserStatsEntity
import io.mishkav.voyager.core.ui.lifecycle.DecomposeViewModel
import io.mishkav.voyager.core.ui.uikit.resultflow.MutableUIResultFlow
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResultFlow
import io.mishkav.voyager.core.ui.uikit.resultflow.asUIResult
import io.mishkav.voyager.core.ui.uikit.resultflow.loadOrError
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Inject
class ProfileViewModel(
    private val userStatsRepository: IUserStatsRepository,
    private val supabaseAuth: Auth,
) : DecomposeViewModel() {

    private val _signOutState = MutableUIResultFlow<Unit>()
    val signOutState: UIResultFlow<Unit> = _signOutState.asStateFlow()

    val statsState: StateFlow<UIResult<UserStatsEntity>> = userStatsRepository.getUserStats()
        .asUIResult()
        .onEach { result -> Logger.d { "KEK: new result - $result" } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UIResult.Loading(),
        )

    fun signOut() {
        viewModelScope.launch {
            _signOutState.loadOrError {
                supabaseAuth.signOut()
            }
        }
    }
}
