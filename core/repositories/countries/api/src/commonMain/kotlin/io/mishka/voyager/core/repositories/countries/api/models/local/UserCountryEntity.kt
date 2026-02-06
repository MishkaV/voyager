package io.mishka.voyager.core.repositories.countries.api.models.local

import androidx.room.Entity

@Entity(tableName = "countries_visited", primaryKeys = ["userId", "countryId"])
data class UserCountryEntity(
    val userId: String,
    val countryId: String,
)
