package io.mishka.voyager.auth.impl.ui.login

import dev.zacsweers.metro.Inject
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Github
import io.mishkav.voyager.core.ui.lifecycle.DecomposeViewModel
import kotlinx.coroutines.launch

@Inject
class LoginViewModel(
    private val supabase: SupabaseClient,
) : DecomposeViewModel() {

    fun startGithubSignIn() {
        viewModelScope.launch {
            supabase.auth.signInWith(Github)
        }
    }
}
