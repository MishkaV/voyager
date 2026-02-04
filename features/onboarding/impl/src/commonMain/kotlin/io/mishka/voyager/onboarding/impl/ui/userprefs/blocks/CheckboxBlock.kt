package io.mishka.voyager.onboarding.impl.ui.userprefs.blocks

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.mishka.voyager.onboarding.impl.ui.userprefs.PrefsUIState
import io.mishka.voyager.onboarding.impl.ui.userprefs.components.PrefCheckbox
import io.mishkav.voyager.core.ui.uikit.shimmer.placeholderFadeConnecting

@Composable
internal fun CheckboxBlock(
    prefsState: State<PrefsUIState>,
    selectedPrefsIds: SnapshotStateList<String>,
) {
    val sharedShape = RoundedCornerShape(16.dp)
    val shimmerWidthWeight = 0.7f

    AnimatedContent(
        targetState = prefsState.value
    ) { state ->
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (state) {
                is PrefsUIState.Error -> Unit
                is PrefsUIState.Loading -> {
                    repeat((0..3).count()) {
                        Box(
                            modifier = Modifier
                                .clip(sharedShape)
                                .fillMaxWidth(shimmerWidthWeight)
                                .height(48.dp)
                                .placeholderFadeConnecting(sharedShape)
                        )
                    }
                }

                is PrefsUIState.Success -> {
                    state.prefs.forEach { pref ->
                        PrefCheckbox(
                            text = pref.title,
                            isSelected = pref.id in selectedPrefsIds,
                            onClick = { isSelected ->
                                if (isSelected) {
                                    selectedPrefsIds.add(pref.id)
                                } else {
                                    selectedPrefsIds.remove(pref.id)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
