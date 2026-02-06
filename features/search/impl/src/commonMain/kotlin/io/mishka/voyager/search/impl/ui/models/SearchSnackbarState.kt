package io.mishka.voyager.search.impl.ui.models

sealed interface SearchSnackbarState {
    val countryName: String

    class CountryVisited(
        override val countryName: String
    ) : SearchSnackbarState

    class CountryUnvisited(
        override val countryName: String
    ) : SearchSnackbarState
}
