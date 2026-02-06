package io.mishka.voyager.search.impl.ui.blocks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import io.mishka.voyager.core.repositories.countries.api.models.local.Continent
import io.mishka.voyager.search.impl.ui.utils.DURATION_ANIMATION
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import org.jetbrains.compose.resources.stringResource
import voyager.features.search.impl.generated.resources.Res
import voyager.features.search.impl.generated.resources.search_tab_title

@Composable
fun TitleBlock(
    continentState: State<Continent?>,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = continentState.value,
        transitionSpec = {
            slideInVertically(
                animationSpec = tween(durationMillis = DURATION_ANIMATION),
            ) { height -> height } + fadeIn(
                animationSpec = tween(durationMillis = DURATION_ANIMATION),
            ) togetherWith slideOutVertically(
                animationSpec = tween(durationMillis = DURATION_ANIMATION),
            ) { height -> -height } + fadeOut(
                animationSpec = tween(durationMillis = DURATION_ANIMATION),
            )
        }
    ) { continent ->
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = continent?.displayName ?: stringResource(Res.string.search_tab_title),
            style = VoyagerTheme.typography.h1,
            textAlign = TextAlign.Start,
            color = VoyagerTheme.colors.font
        )
    }
}
