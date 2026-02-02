package io.mishkav.voyager.core.ui.uikit.snackbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.theme.icons.close24
import io.mishkav.voyager.core.ui.theme.icons.error36

@Composable
fun VoyagerErrorSnackbar(
    text: String,
    modifier: Modifier = Modifier,
    close: (() -> Unit)? = null,
    tryAgain: (() -> Unit)? = null,
) {
    if (close != null && tryAgain != null) {
        error("One of the 'close' or 'tryAgain' parameters must be null")
    }

    BaseVoyagerSnackbar(
        text = text,
        modifier = modifier,
        icon = {
            Icon(
                modifier = Modifier.size(36.dp),
                imageVector = VoyagerTheme.icons.error36,
                contentDescription = null,
                tint = VoyagerTheme.colors.font,
            )
        },
        trailingIcon = {
            close?.let {
                Icon(
                    modifier = Modifier.clickable { close() },
                    imageVector = VoyagerTheme.icons.close24,
                    contentDescription = null,
                    tint = VoyagerTheme.colors.font,
                )
            }

            tryAgain?.let {
                BaseVoyagerSnackbarButton(
                    text = "Try again",
                    contentColor = VoyagerTheme.colors.black,
                    backgroundColor = VoyagerTheme.colors.white,
                    onClick = tryAgain,
                )
            }
        },
    )
}
