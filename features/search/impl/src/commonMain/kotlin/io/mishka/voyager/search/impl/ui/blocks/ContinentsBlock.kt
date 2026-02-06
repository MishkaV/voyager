package io.mishka.voyager.search.impl.ui.blocks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.mishka.voyager.core.repositories.countries.api.models.local.Continent
import io.mishka.voyager.search.impl.ui.utils.itemBackground
import io.mishka.voyager.search.impl.ui.utils.itemImageRes
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.utils.clickableUnindicated
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import voyager.features.search.impl.generated.resources.Res
import voyager.features.search.impl.generated.resources.search_title_all
import voyager.features.search.impl.generated.resources.search_title_by_continents

fun LazyListScope.continentsBlock(
    isContinentContentVisible: State<Boolean>,
    selectContinent: (Continent) -> Unit,
) {
    item(
        key = "CONTINENTS_KEY",
        contentType = "CONTINENTS_TYPE",
    ) {
        AnimatedVisibility(
            visible = isContinentContentVisible.value,
            enter = slideInVertically() + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            ContinentsBlock(
                selectContinent = selectContinent,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun ContinentsBlock(
    selectContinent: (Continent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.search_title_by_continents),
            style = VoyagerTheme.typography.h2,
            color = VoyagerTheme.colors.font,
        )

        Spacer(Modifier.height(14.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Continent.entries.chunked(2).forEach { rowContinents ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    rowContinents.forEach { continent ->
                        ContinentItem(
                            continent = continent,
                            onClick = selectContinent,
                            modifier = Modifier.weight(1f).height(90.dp)
                        )
                    }
                    // Add spacer if last row has only one item
                    if (rowContinents.size == 1) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }

        Spacer(Modifier.height(44.dp))

        // Add next title here for easy animation
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.search_title_all),
            style = VoyagerTheme.typography.h2,
            color = VoyagerTheme.colors.font,
        )

        Spacer(Modifier.height(14.dp))
    }
}

@Composable
private fun ContinentItem(
    continent: Continent,
    onClick: (Continent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(continent.itemBackground)
            .clickableUnindicated(onClick = { onClick(continent) }),
        verticalAlignment = Alignment.Top,
    ) {
        Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = continent.displayName,
                style = VoyagerTheme.typography.h3,
                color = VoyagerTheme.colors.font,
            )
        }

        Image(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            painter = painterResource(continent.itemImageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}
