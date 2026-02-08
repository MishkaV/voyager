package io.mishka.voyager.details.impl.ui.details.blocks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryOverviewEntity
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import io.mishkav.voyager.core.ui.uikit.shimmer.placeholderFadeConnecting
import io.mishkav.voyager.core.ui.uikit.utils.clickableUnindicated
import io.mishkav.voyager.core.ui.uikit.utils.safeOpenUri
import org.jetbrains.compose.resources.stringResource
import voyager.features.details.impl.generated.resources.Res
import voyager.features.details.impl.generated.resources.details_overview
import voyager.features.details.impl.generated.resources.details_overview_wiki

internal fun LazyListScope.overviewBlock(
    overviewState: State<UIResult<CountryOverviewEntity?>>,
) {
    item(
        key = "OVERVIEW_BLOCK_KEY",
        contentType = "OVERVIEW_BLOCK_TYPE",
    ) {
        OverviewBlock(
            overviewState = overviewState,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun OverviewBlock(
    overviewState: State<UIResult<CountryOverviewEntity?>>,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

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
            text = stringResource(Res.string.details_overview),
            style = VoyagerTheme.typography.h3,
            color = VoyagerTheme.colors.white,
            textAlign = TextAlign.Start,
        )

        Spacer(Modifier.height(8.dp))

        AnimatedContent(
            targetState = overviewState.value,
            transitionSpec = {
                fadeIn() + slideInVertically() + expandVertically() togetherWith fadeOut()
            },
        ) { state ->
            when (state) {
                is UIResult.Success -> {
                    state.data?.let { overview ->
                        OverviewContent(
                            description = overview.body,
                            modifier = Modifier.fillMaxWidth().clickableUnindicated {
                                uriHandler.safeOpenUri(overview.wikipediaUrl)
                            },
                        )
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(126.dp)
                            .placeholderFadeConnecting(
                                shape = RoundedCornerShape(8.dp),
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun OverviewContent(
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = description,
            style = VoyagerTheme.typography.body,
            color = VoyagerTheme.colors.white,
            textAlign = TextAlign.Start,
        )

        Spacer(Modifier.height(12.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.details_overview_wiki),
            style = VoyagerTheme.typography.caption,
            color = VoyagerTheme.colors.white,
            textAlign = TextAlign.Start,
        )
    }
}
