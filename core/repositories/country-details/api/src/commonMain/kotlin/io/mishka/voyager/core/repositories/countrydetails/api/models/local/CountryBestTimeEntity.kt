package io.mishka.voyager.core.repositories.countrydetails.api.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_best_times")
data class CountryBestTimeEntity(
    @PrimaryKey val id: String,
    val countryId: String,
    val title: String,
    val description: String,
)
