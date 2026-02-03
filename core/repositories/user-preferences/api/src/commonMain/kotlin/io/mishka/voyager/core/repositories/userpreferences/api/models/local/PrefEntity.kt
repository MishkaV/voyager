package io.mishka.voyager.core.repositories.userpreferences.api.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for storing preference categories locally.
 * This is a global list of available preferences, cached for quick access.
 */
@Entity(tableName = "prefs")
data class PrefEntity(
    @PrimaryKey
    val id: String,
    val title: String,
)
