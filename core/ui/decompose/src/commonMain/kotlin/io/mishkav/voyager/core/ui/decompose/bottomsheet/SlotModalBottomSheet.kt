package io.mishkav.voyager.core.ui.decompose.bottomsheet

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop

@Composable
fun <T : Any, K : Any> SlotModalBottomSheet(
    childSlotValue: Value<ChildSlot<T, K>>,
    onDismiss: () -> Unit,
    skipPartiallyExpanded: Boolean = true,
    content: @Composable ColumnScope.(K) -> Unit
) {
    val childSlot by childSlotValue.subscribeAsState()
    SlotModalBottomSheet(
        childSlot = childSlot,
        skipPartiallyExpanded = skipPartiallyExpanded,
        onDismiss = onDismiss,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any, K : Any> SlotModalBottomSheet(
    childSlot: ChildSlot<T, K>,
    onDismiss: () -> Unit,
    skipPartiallyExpanded: Boolean = true,
    content: @Composable ColumnScope.(K) -> Unit
) {
    val colors = VoyagerTheme.colors
    val child = childSlot.child?.instance

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded,
        confirmValueChange = {
            true
        }
    )

    LaunchedEffect(sheetState) {
        snapshotFlow { sheetState.isVisible }
            .distinctUntilChanged()
            .drop(1)
            .collect { visible ->
                if (!visible) {
                    onDismiss.invoke()
                }
            }
    }

    if (child != null) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss.invoke() },
            sheetState = sheetState,
            content = {
                content.invoke(this, child)
            },
            dragHandle = null,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = Color.Transparent,
            contentColor = colors.font,
        )
    }
}
