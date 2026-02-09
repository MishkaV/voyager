package io.mishka.voyager.core.repositories.countrydetails.api.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_ai_suggests")
data class CountryAiSuggestEntity(
    @PrimaryKey val id: String,
    val countryId: String,
    val suggestText: String,
    val prompt: String,
)
