package io.mishka.voyager.onboarding.impl.ui.vibes.blocks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateSet
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mishka.voyager.core.repositories.vibes.api.models.local.VibeCategoryWithVibes
import io.mishka.voyager.onboarding.impl.ui.vibes.VibesUIState
import io.mishka.voyager.onboarding.impl.ui.vibes.components.VibeBox
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.shimmer.placeholderFadeConnecting

fun LazyListScope.vibesListBlock(
    selectedVibeIds: SnapshotStateSet<String>,
    vibesState: State<VibesUIState>,
) {
    when (val state = vibesState.value) {
        is VibesUIState.Error -> Unit
        is VibesUIState.Loading -> {
            item(
                key = "Loading"
            ) {
                LoadingList(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        is VibesUIState.Success -> {
            items(
                items = state.vibes,
                key = { it.category.id },
                contentType = { "VIBE_CATEGORY_BLOCK" }
            ) { item ->
                CategoryBlock(
                    selectedVibeIds = selectedVibeIds,
                    categoryWithVibes = item,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            item {
                Spacer(Modifier.height(64.dp))
            }
        }
    }
}

@Composable
private fun LoadingList(
    modifier: Modifier = Modifier,
) {
    val shimmerWidthWeight = 0.4f

    Column(
        modifier = modifier,
    ) {
        repeat((0..3).count()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(shimmerWidthWeight)
                    .height(24.dp)
                    .placeholderFadeConnecting()
            )

            Spacer(Modifier.height(12.dp))

            FlowColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            ) {
                repeat((0..5).count()) {
                    Box(
                        modifier = Modifier
                            .width(90.dp)
                            .height(40.dp)
                            .placeholderFadeConnecting()
                    )
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun CategoryBlock(
    selectedVibeIds: SnapshotStateSet<String>,
    categoryWithVibes: VibeCategoryWithVibes,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = categoryWithVibes.category.title,
            style = VoyagerTheme.typography.h2,
            textAlign = TextAlign.Start,
            color = VoyagerTheme.colors.font
        )

        Spacer(Modifier.height(12.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        ) {
            categoryWithVibes.vibes.forEach { vibe ->
                VibeBox(
                    vibe = vibe,
                    isSelected = vibe.id in selectedVibeIds,
                    onClick = { isSelected ->
                        if (isSelected) {
                            selectedVibeIds.add(vibe.id)
                        } else {
                            selectedVibeIds.remove(vibe.id)
                        }
                    }
                )
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}
