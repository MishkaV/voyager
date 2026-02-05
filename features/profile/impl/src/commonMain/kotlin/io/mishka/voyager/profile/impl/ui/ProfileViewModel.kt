package io.mishka.voyager.profile.impl.ui

import dev.zacsweers.metro.Inject
import io.github.jan.supabase.auth.Auth
import io.mishkav.voyager.core.ui.lifecycle.DecomposeViewModel
import io.mishkav.voyager.core.ui.uikit.resultflow.MutableUIResultFlow
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResultFlow
import io.mishkav.voyager.core.ui.uikit.resultflow.loadOrError
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Inject
class ProfileViewModel(
    private val supabaseAuth: Auth
) : DecomposeViewModel() {

    private val _signOutState = MutableUIResultFlow<Unit>()
    val signOutState: UIResultFlow<Unit> = _signOutState.asStateFlow()

    fun signOut() {
        viewModelScope.launch {
            _signOutState.loadOrError {
                supabaseAuth.signOut()
            }
        }
    }
}
