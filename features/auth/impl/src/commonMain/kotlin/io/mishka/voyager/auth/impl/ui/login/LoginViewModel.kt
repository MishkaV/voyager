package io.mishka.voyager.auth.impl.ui.login

import dev.zacsweers.metro.Inject
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.mishkav.voyager.core.ui.lifecycle.DecomposeViewModel

@Inject
class LoginViewModel(
    val supabaseComposeAuth: ComposeAuth
) : DecomposeViewModel()
