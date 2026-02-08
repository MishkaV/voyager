package io.mishka.voyager.details.impl.ui.blocks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mishka.voyager.core.repositories.countries.api.models.local.CountryWithVisitedStatus
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import io.mishkav.voyager.core.ui.uikit.resultflow.isLoading
import io.mishkav.voyager.core.ui.uikit.resultflow.successOrNull
import io.mishkav.voyager.core.ui.uikit.shimmer.placeholderFadeConnecting
import org.jetbrains.compose.resources.stringResource
import voyager.features.details.impl.generated.resources.Res
import voyager.features.details.impl.generated.resources.details_capital
import voyager.features.details.impl.generated.resources.details_currency
import voyager.features.details.impl.generated.resources.details_language

internal fun LazyListScope.generalInfoBlock(
    countryState: State<UIResult<CountryWithVisitedStatus>>,
) {
    item(
        key = "GENERAL_INFO_BLOCK_KEY",
        contentType = "GENERAL_INFO_BLOCK",
    ) {
        GeneralInfoBlock(
            countryState = countryState,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun GeneralInfoBlock(
    countryState: State<UIResult<CountryWithVisitedStatus>>,
    modifier: Modifier = Modifier,
) {
    val isLoading = remember(countryState.value) {
        derivedStateOf { countryState.value.isLoading() }
    }
    val sharedPlaceholderModifier = Modifier.placeholderFadeConnecting(
        shape = RoundedCornerShape(16.dp),
        visible = isLoading.value
    )

    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InfoBox(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .then(sharedPlaceholderModifier),
                topic = stringResource(Res.string.details_language),
                text = countryState.value.successOrNull()?.country?.primaryLanguage.orEmpty()
            )

            Spacer(Modifier.width(8.dp))

            InfoBox(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .then(sharedPlaceholderModifier),
                topic = stringResource(Res.string.details_currency),
                text = countryState.value.successOrNull()?.country?.primaryCurrency.orEmpty()
            )
        }

        Spacer(Modifier.height(12.dp))

        InfoBox(
            modifier = Modifier
                .fillMaxWidth()
                .then(sharedPlaceholderModifier),
            topic = stringResource(Res.string.details_capital),
            text = countryState.value.successOrNull()?.country?.capital.orEmpty()
        )
    }
}

@Composable
private fun InfoBox(
    topic: String,
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .heightIn(70.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(VoyagerTheme.colors.white.copy(alpha = 0.2f))
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = topic,
            style = VoyagerTheme.typography.caption,
            color = VoyagerTheme.colors.white.copy(alpha = 0.7f),
            textAlign = TextAlign.Start,
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = text,
            style = VoyagerTheme.typography.h2,
            color = VoyagerTheme.colors.white,
            textAlign = TextAlign.Start,
        )
    }
}
