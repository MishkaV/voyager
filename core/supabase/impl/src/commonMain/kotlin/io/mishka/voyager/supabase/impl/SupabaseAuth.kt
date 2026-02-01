package io.mishka.voyager.supabase.impl

import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import io.mishka.voyager.supabase.api.ISupabaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ContributesBinding(AppScope::class)
class SupabaseAuth(
    private val supabase: SupabaseClient,
) : ISupabaseAuth {
    private val auth: Auth by lazy { supabase.auth }

    override val userFlow: Flow<UserInfo?> = auth.sessionStatus.map { status ->
        when (status) {
            is SessionStatus.Authenticated -> status.session.user
            else -> null
        }
    }

    override suspend fun getCurrentUser(): UserInfo? {
        auth.awaitInitialization()

        return auth.currentUserOrNull()
    }

    override suspend fun isLoggedIn(): Boolean {
        auth.awaitInitialization()

        val currentSession = auth.currentSessionOrNull()
        return (currentSession != null).also {
            Logger.d("SupabaseAuth: isLoggedIn - $it")
        }
    }
}
