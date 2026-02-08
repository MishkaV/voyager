package io.mishka.voyager.details.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.mishka.voyager.details.api.models.CountryDetailsArgs
import io.mishka.voyager.details.impl.ui.blocks.appBarBlock
import io.mishka.voyager.details.impl.ui.blocks.titleBlock
import io.mishka.voyager.details.impl.ui.utils.toComposeColor

@Composable
fun CountryDetailsScreen(
    args: CountryDetailsArgs,
    viewModel: CountryDetailsViewModel,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CountryDetailsScreenContent(
        modifier = modifier,
        args = args,
        navigateBack = navigateBack,
        addOrRemoveVisitedCounty = { isVisited ->
            // TODO
        }
    )
}

@Composable
private fun CountryDetailsScreenContent(
    args: CountryDetailsArgs,
    navigateBack: () -> Unit,
    addOrRemoveVisitedCounty: (isVisited: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .background(args.backgroundHex.toComposeColor())
            .padding(horizontal = 24.dp)
            .windowInsetsPadding(WindowInsets.navigationBars),
    ) {
        appBarBlock(
            isVisited = false, // TODO
            navigateBack = navigateBack,
            addOrRemoveVisitedCounty = addOrRemoveVisitedCounty,
        )

        item(contentType = "SPACER") { Spacer(Modifier.height(32.dp)) }

        titleBlock(
            countryName = args.name,
            flagFullPatch = args.flagFullPatch,
        )

        item(contentType = "SPACER") { Spacer(Modifier.height(34.dp)) }
    }
}
