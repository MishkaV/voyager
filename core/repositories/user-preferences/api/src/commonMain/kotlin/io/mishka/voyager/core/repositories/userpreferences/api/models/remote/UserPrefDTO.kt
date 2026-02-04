package io.mishka.voyager.core.repositories.userpreferences.api.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for user preference selection from Supabase API.
 * Used for adding user preferences (backend only, no local storage).
 */
@Serializable
data class UserPrefDTO(
    @SerialName("pref_id")
    val prefId: String,
    @SerialName("user_id")
    val userId: String,
)
