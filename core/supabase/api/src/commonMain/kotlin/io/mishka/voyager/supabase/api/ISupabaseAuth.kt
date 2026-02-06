package io.mishka.voyager.supabase.api

import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface ISupabaseAuth {
    /**
     * Flow for tracking current user state
     * Updates automatically on sign in/sign out
     */
    val userFlow: Flow<UserInfo?>

    /**
     * Get current authenticated user
     * @return UserInfo if user is logged in, null otherwise
     */
    suspend fun getAsyncCurrentUser(): UserInfo?

    /**
     * Get current authenticated user
     * @return UserInfo if user is logged in, null otherwise
     */
    fun getCurrentUser(): UserInfo?

    /**
     * Check if user is logged in
     * @return true if user is authenticated, false otherwise
     */
    suspend fun isLoggedIn(): Boolean
}
