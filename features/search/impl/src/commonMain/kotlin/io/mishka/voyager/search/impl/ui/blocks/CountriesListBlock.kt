package io.mishka.voyager.search.impl.ui.blocks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryWithVisitedStatus
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.theme.icons.location24
import io.mishkav.voyager.core.ui.uikit.image.VoyagerImage
import io.mishkav.voyager.core.ui.uikit.shimmer.placeholderFadeConnecting
import io.mishkav.voyager.core.ui.uikit.utils.clickableUnindicated
import io.mishkav.voyager.core.utils.supabase.voyagerAuthenticatedStorageItem
import org.jetbrains.compose.resources.stringResource
import voyager.features.search.impl.generated.resources.Res
import voyager.features.search.impl.generated.resources.search_empty_subtitle
import voyager.features.search.impl.generated.resources.search_empty_title

private const val MOCK_ITEMS_COUNT = 20

fun LazyListScope.countriesListBlock(
    addOrRemoveVisitedCounty: (CountryWithVisitedStatus) -> Unit,
    navigateToCountryInfo: (CountryWithVisitedStatus) -> Unit,
    countriesState: LazyPagingItems<CountryWithVisitedStatus>,
) {
    val sharedModifier = Modifier.fillMaxWidth()

    with(countriesState) {
        when {
            loadState.refresh is LoadState.Loading && itemCount == 0 -> {
                items(
                    count = MOCK_ITEMS_COUNT,
                    contentType = { "MOCK_COUNTY_TYPE" }
                ) {
                    LoadingCountryBox(modifier = sharedModifier)
                }
            }

            loadState.append is LoadState.NotLoading && loadState.append.endOfPaginationReached && itemCount == 0 -> {
                item(
                    key = "EMPTY_LIST_KEY",
                    contentType = "EMPTY_LIST_TYPE",
                ) {
                    EmptyBox(modifier = sharedModifier)
                }
            }

            else -> {
                items(
                    count = countriesState.itemCount,
                    key = countriesState.itemKey { it.country.id },
                    contentType = countriesState.itemContentType { "COUNTRY_TYPE" }
                ) { index ->
                    countriesState[index]?.let { country ->
                        CountryBox(
                            country = country,
                            onVisitClick = { isVisited ->
                                addOrRemoveVisitedCounty(
                                    country.copy(isVisited = !isVisited)
                                )
                            },
                            onCountryClick = navigateToCountryInfo,
                            modifier = sharedModifier,
                        )
                    } ?: LoadingCountryBox(modifier = sharedModifier)
                }
            }
        }
    }
}

@Composable
private fun CountryBox(
    country: CountryWithVisitedStatus,
    onVisitClick: (isVisited: Boolean) -> Unit,
    onCountryClick: (CountryWithVisitedStatus) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickableUnindicated(onClick = { onCountryClick(country) })
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        VoyagerImage(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            data = voyagerAuthenticatedStorageItem(country.country.flagFullPatch),
            contentDescription = null,
            shape = CircleShape,
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(16.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = country.country.name,
            style = VoyagerTheme.typography.h3,
            color = VoyagerTheme.colors.font,
        )

        Spacer(Modifier.width(16.dp))

        Icon(
            modifier = Modifier.clickableUnindicated {
                onVisitClick(!country.isVisited)
            },
            imageVector = VoyagerTheme.icons.location24,
            contentDescription = null,
            tint = if (country.isVisited) VoyagerTheme.colors.font else VoyagerTheme.colors.disabled,
        )
    }
}

@Composable
private fun LoadingCountryBox(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .placeholderFadeConnecting(shape = CircleShape),
        )

        Spacer(Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .placeholderFadeConnecting(),
        )
    }
}

@Composable
private fun EmptyBox(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.search_empty_title),
            style = VoyagerTheme.typography.h2,
            color = VoyagerTheme.colors.font,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.search_empty_subtitle),
            style = VoyagerTheme.typography.bodyBold,
            color = VoyagerTheme.colors.font,
            textAlign = TextAlign.Center
        )
    }
}
