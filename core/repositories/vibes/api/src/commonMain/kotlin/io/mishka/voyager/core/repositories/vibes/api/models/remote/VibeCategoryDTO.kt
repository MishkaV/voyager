package io.mishka.voyager.core.repositories.vibes.api.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VibeCategoryDTO(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
)
