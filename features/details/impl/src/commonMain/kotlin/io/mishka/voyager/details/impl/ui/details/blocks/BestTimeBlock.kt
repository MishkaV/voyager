package io.mishka.voyager.details.impl.ui.details.blocks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryBestTimeEntity
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import io.mishkav.voyager.core.ui.uikit.shimmer.placeholderFadeConnecting
import org.jetbrains.compose.resources.stringResource
import voyager.features.details.impl.generated.resources.Res
import voyager.features.details.impl.generated.resources.details_best_time

internal fun LazyListScope.bestTimeBlock(
    bestTimesState: State<UIResult<List<CountryBestTimeEntity>>>,
) {
    item(
        key = "BEST_TIME_BLOCK_KEY",
        contentType = "BEST_TIME_BLOCK_TYPE",
    ) {
        BestTimeBlock(
            bestTimesState = bestTimesState,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun BestTimeBlock(
    bestTimesState: State<UIResult<List<CountryBestTimeEntity>>>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(VoyagerTheme.colors.white.copy(alpha = 0.2f))
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp
            ),
    ) {
        Text(
            text = stringResource(Res.string.details_best_time),
            style = VoyagerTheme.typography.caption,
            color = VoyagerTheme.colors.white.copy(alpha = 0.7f),
            textAlign = TextAlign.Start,
        )

        Spacer(Modifier.height(6.dp))

        AnimatedContent(
            targetState = bestTimesState.value,
            transitionSpec = {
                fadeIn() + slideInVertically() + expandVertically() togetherWith fadeOut()
            }
        ) { state ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                when (state) {
                    is UIResult.Success -> {
                        state.data.forEach { item ->
                            BestTimeItem(
                                title = item.title,
                                description = item.description,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }

                    else -> {
                        repeat(2) {
                            BestTimeItem(
                                title = "",
                                description = "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .placeholderFadeConnecting(
                                        shape = RoundedCornerShape(8.dp),
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BestTimeItem(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(VoyagerTheme.colors.white.copy(alpha = 0.2f))
            .padding(12.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = title,
            style = VoyagerTheme.typography.h2,
            color = VoyagerTheme.colors.white,
            textAlign = TextAlign.Start,
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = description,
            style = VoyagerTheme.typography.body,
            color = VoyagerTheme.colors.white,
            textAlign = TextAlign.Start,
        )
    }
}
