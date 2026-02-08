package io.mishka.voyager.details.impl.ui.details.blocks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryAiSuggestEntity
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import io.mishkav.voyager.core.ui.uikit.shimmer.placeholderFadeConnecting
import io.mishkav.voyager.core.ui.uikit.utils.clickableUnindicated
import org.jetbrains.compose.resources.stringResource
import voyager.features.details.impl.generated.resources.Res
import voyager.features.details.impl.generated.resources.details_voyager_ai

fun LazyListScope.voyagerAIBlock(
    aiSuggestsState: State<UIResult<List<CountryAiSuggestEntity>>>,
    requestSuggest: (aiSuggestId: String) -> Unit,
) {
    item(
        key = "VOYAGER_AI_BLOCK_KEY",
        contentType = "VOYAGER_AI_BLOCK_TYPE",
    ) {
        VoyagerAIBlock(
            aiSuggestsState = aiSuggestsState,
            requestSuggest = requestSuggest,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun VoyagerAIBlock(
    aiSuggestsState: State<UIResult<List<CountryAiSuggestEntity>>>,
    requestSuggest: (aiSuggestId: String) -> Unit,
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
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(Res.string.details_voyager_ai),
            style = VoyagerTheme.typography.h3,
            color = VoyagerTheme.colors.white,
            textAlign = TextAlign.Start,
        )

        Spacer(Modifier.height(8.dp))

        AnimatedContent(
            targetState = aiSuggestsState.value,
            transitionSpec = {
                fadeIn() + slideInVertically() + expandVertically() togetherWith fadeOut()
            },
        ) { state ->
            when (state) {
                is UIResult.Success -> {
                    SuggestsContent(
                        state = state,
                        requestSuggest = requestSuggest,
                    )
                }

                else -> {
                    LoadingContent()
                }
            }
        }
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier,
) {
    val itemsToShow = 5

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        repeat(itemsToShow) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .placeholderFadeConnecting(
                        shape = RoundedCornerShape(8.dp),
                    )
            )
        }
    }
}

@Composable
private fun SuggestsContent(
    state: UIResult.Success<List<CountryAiSuggestEntity>>,
    requestSuggest: (aiSuggestId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        state.data.forEach { suggest ->
            AiSuggestItem(
                text = suggest.suggestText,
                onClick = {
                    requestSuggest(suggest.id)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AiSuggestItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(VoyagerTheme.colors.white.copy(alpha = 0.2f))
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp
            )
            .clickableUnindicated(onClick = onClick),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            style = VoyagerTheme.typography.body,
            color = VoyagerTheme.colors.white,
            textAlign = TextAlign.Start,
        )
    }
}
