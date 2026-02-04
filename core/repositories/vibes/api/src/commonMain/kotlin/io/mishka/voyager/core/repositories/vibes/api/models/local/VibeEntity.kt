package io.mishka.voyager.core.repositories.vibes.api.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vibes")
data class VibeEntity(
    @PrimaryKey
    val id: String,
    val categoryId: String,
    val title: String,
    val iconEmoji: String,
)
