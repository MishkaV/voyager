package io.mishka.voyager.auth.impl.ui.login.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.jetbrains.compose.resources.stringResource
import voyager.features.auth.impl.generated.resources.Res
import voyager.features.auth.impl.generated.resources.login_title_1
import voyager.features.auth.impl.generated.resources.login_title_2
import voyager.features.auth.impl.generated.resources.login_title_3

private const val DELAY_TEXT_APPEND = 70L
private const val DELAY_TEXT_REFRESH = 400L

@Composable
internal fun TypewriterText(
    modifier: Modifier = Modifier,
) {
    var textToDisplay by remember { mutableStateOf("") }
    val finalTexts = listOf(
        stringResource(Res.string.login_title_1),
        stringResource(Res.string.login_title_2),
        stringResource(Res.string.login_title_3),
    )

    LaunchedEffect(Unit) {
        var index = 0

        while (isActive) {
            // Reset and pick current text
            textToDisplay = ""
            val currentText = finalTexts[index % finalTexts.size]

            // Start type write
            for (i in currentText.toCharArray()) {
                textToDisplay += i.toString()
                delay(DELAY_TEXT_APPEND)
            }

            // Wait before new loop
            delay(DELAY_TEXT_REFRESH)
            index++
        }
    }

    Text(
        modifier = modifier,
        text = textToDisplay,
        style = VoyagerTheme.typography.h1,
        textAlign = TextAlign.Center,
        color = VoyagerTheme.colors.font
    )
}
