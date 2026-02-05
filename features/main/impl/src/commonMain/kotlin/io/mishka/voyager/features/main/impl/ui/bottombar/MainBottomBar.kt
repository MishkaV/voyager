package io.mishka.voyager.features.main.impl.ui.bottombar

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mishka.voyager.features.main.impl.domain.model.MainBottomTab
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.utils.clickableUnindicated
import org.jetbrains.compose.resources.stringResource
import voyager.features.main.impl.generated.resources.Res
import voyager.features.main.impl.generated.resources.tab_home
import voyager.features.main.impl.generated.resources.tab_profile
import voyager.features.main.impl.generated.resources.tab_search

private val MAIN_BOTTOM_BAR_HEIGHT = 56.dp

@Composable
fun MainBottomBar(
    selectedTab: MainBottomTab,
    onTabClick: (tab: MainBottomTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs = remember { MainBottomTab.entries.toTypedArray() }

    Row(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        VoyagerTheme.colors.black.copy(alpha = 0.7f),
                    )
                )
            )
            .windowInsetsPadding(WindowInsets.systemBars)
            .fillMaxWidth()
            .height(MAIN_BOTTOM_BAR_HEIGHT)
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        tabs.forEach { tab ->
            TabIcon(
                tab = tab,
                isSelected = tab == selectedTab,
                onClick = { onTabClick(tab) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun TabIcon(
    tab: MainBottomTab,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val alpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.35f,
        label = "alpha",
    )
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.98f,
        visibilityThreshold = .000001f,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioMediumBouncy,
        ),
        label = "scale",
    )
    Column(
        modifier = modifier
            .graphicsLayer {
                this.scaleX = scale
                this.scaleY = scale
                this.alpha = alpha
            }
            .clickableUnindicated(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.height(24.dp),
            imageVector = tab.icon,
            contentDescription = null,
            tint = VoyagerTheme.colors.font,
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = stringResource(tab.titleRes),
            style = VoyagerTheme.typography.body,
            color = VoyagerTheme.colors.font,
            textAlign = TextAlign.Center,
        )
    }
}

private val MainBottomTab.titleRes
    get() =
        when (this) {
            MainBottomTab.HOME -> Res.string.tab_home
            MainBottomTab.SEARCH -> Res.string.tab_search
            MainBottomTab.PROFILE -> Res.string.tab_profile
        }
