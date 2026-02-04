package io.mishka.voyager.core.repositories.vibes.api.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vibe_categories")
data class VibeCategoryEntity(
    @PrimaryKey
    val id: String,
    val title: String,
)
