package io.mishka.voyager.core.repositories.countrydetails.api.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_podcasts")
data class CountryPodcastEntity(
    @PrimaryKey val id: String,
    val countryId: String,
    val audioFullPatch: String,
    val title: String,
    val subtitle: String,
    val durationSec: Int,
)
