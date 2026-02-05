package io.mishka.voyager.profile.impl.ui.blocks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mishka.voyager.core.repositories.userstats.api.models.local.UserStatsEntity
import io.mishka.voyager.profile.impl.ui.utils.isWhole
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import voyager.features.profile.impl.generated.resources.Res
import voyager.features.profile.impl.generated.resources.profile_stat_explored_description
import voyager.features.profile.impl.generated.resources.profile_stat_visited_continents
import voyager.features.profile.impl.generated.resources.profile_stat_visited_countries
import java.util.Locale

@Composable
internal fun StatsBlock(
    statsState: State<UIResult<UserStatsEntity>>,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = statsState.value,
        transitionSpec = { fadeIn() + slideInVertically() + expandVertically() togetherWith fadeOut() },
    ) { state ->
        when (state) {
            is UIResult.Error -> Unit
            is UIResult.Loading -> Unit
            is UIResult.Success -> {
                val defaultLocale = Locale.getDefault()
                val exploredPercentage = if (state.data.worldExploredPercent.isWhole()) {
                    String.format(defaultLocale, "%.0f%%", state.data.worldExploredPercent)
                } else {
                    String.format(defaultLocale, "%.1f%%", state.data.worldExploredPercent)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(VoyagerTheme.colors.brand)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    StatsItem(
                        countText = state.data.countriesVisited.toString(),
                        descriptionText = pluralStringResource(
                            Res.plurals.profile_stat_visited_countries,
                            state.data.countriesVisited
                        ),
                    )

                    StatsItem(
                        countText = exploredPercentage,
                        descriptionText = stringResource(Res.string.profile_stat_explored_description),
                    )

                    StatsItem(
                        countText = state.data.continentsVisited.toString(),
                        descriptionText = pluralStringResource(
                            Res.plurals.profile_stat_visited_continents,
                            state.data.continentsVisited
                        ),
                    )
                }
            }
        }
    }
}

@Composable
private fun StatsItem(
    countText: String,
    descriptionText: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = countText,
            style = VoyagerTheme.typography.h3,
            textAlign = TextAlign.Center,
            color = VoyagerTheme.colors.font
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = descriptionText,
            style = VoyagerTheme.typography.body,
            textAlign = TextAlign.Center,
            color = VoyagerTheme.colors.font
        )
    }
}