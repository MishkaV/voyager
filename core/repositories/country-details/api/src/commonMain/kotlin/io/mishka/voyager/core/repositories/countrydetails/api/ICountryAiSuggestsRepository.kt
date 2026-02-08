package io.mishka.voyager.core.repositories.countrydetails.api

import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryAiSuggestEntity

interface ICountryAiSuggestsRepository {

    suspend fun getByCountryId(countryId: String, forceUpdate: Boolean = false): Result<List<CountryAiSuggestEntity>>

    suspend fun generateAiSuggest(aiSuggestId: String, countryId: String): Result<String>
}
