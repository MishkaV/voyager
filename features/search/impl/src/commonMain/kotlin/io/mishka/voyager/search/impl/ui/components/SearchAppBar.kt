package io.mishka.voyager.search.impl.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.mishka.voyager.search.impl.ui.utils.DURATION_ANIMATION
import io.mishkav.voyager.core.ui.uikit.appbar.SimpleVoyagerAppBar

@Composable
internal fun SearchAppBar(
    isVisible: Boolean,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = DURATION_ANIMATION)
        ) + expandVertically(
            animationSpec = tween(durationMillis = DURATION_ANIMATION),
            expandFrom = Alignment.Top
        ) + fadeIn(
            animationSpec = tween(durationMillis = DURATION_ANIMATION),
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = DURATION_ANIMATION)
        ) + shrinkVertically(
            animationSpec = tween(durationMillis = DURATION_ANIMATION)
        ) + fadeOut(
            animationSpec = tween(durationMillis = DURATION_ANIMATION),
        ),
    ) {
        Column {
            SimpleVoyagerAppBar(onBack = onBack)

            Spacer(Modifier.height(32.dp))
        }
    }
}
