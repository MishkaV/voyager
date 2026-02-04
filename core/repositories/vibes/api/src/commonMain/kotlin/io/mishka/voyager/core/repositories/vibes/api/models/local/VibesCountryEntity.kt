package io.mishka.voyager.core.repositories.vibes.api.models.local

import androidx.room.Entity

@Entity(tableName = "vibes_country", primaryKeys = ["countryId", "vibeId"])
data class VibesCountryEntity(
    val countryId: String,
    val vibeId: String,
)
