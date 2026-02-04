package io.mishka.voyager.core.repositories.userpreferences.api.models.remote

import kotlinx.serialization.Serializable

/**
 * DTO for preference data from Supabase API.
 */
@Serializable
data class PrefDTO(
    val id: String,
    val title: String,
)
