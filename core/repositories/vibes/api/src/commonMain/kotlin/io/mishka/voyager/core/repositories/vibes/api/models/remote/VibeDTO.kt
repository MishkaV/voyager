package io.mishka.voyager.core.repositories.vibes.api.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VibeDTO(
    @SerialName("id")
    val id: String,
    @SerialName("category_id")
    val categoryId: String,
    @SerialName("title")
    val title: String,
    @SerialName("icon_emoji")
    val iconEmoji: String,
)
