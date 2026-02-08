package io.mishka.voyager.core.repositories.countrydetails.api.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_overview")
data class CountryOverviewEntity(
    @PrimaryKey val id: String,
    val countryId: String,
    val body: String,
    val wikipediaUrl: String,
)
