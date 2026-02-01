package io.mishka.voyager.intro.impl.ui

import co.touchlab.kermit.Logger
import dev.zacsweers.metro.Inject
import io.mishka.voyager.intro.impl.api.IntroState
import io.mishka.voyager.supabase.api.ISupabaseAuth
import io.mishkav.voyager.core.ui.lifecycle.DecomposeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Inject
class IntroViewModel(
    private val supabaseAuth: ISupabaseAuth,
) : DecomposeViewModel() {

    private val _introState = MutableStateFlow<IntroState?>(null)
    val introState = _introState.asStateFlow()

    init {
        init()
    }

    fun init() {
        viewModelScope.launch {
            _introState.value = if (supabaseAuth.isLoggedIn()) {
                IntroState.ShouldShowOnboarding
            } else {
                IntroState.ShouldShowAuth
            }

            Logger.d("IntroViewModel: introState - ${introState.value}")
        }
    }
}
