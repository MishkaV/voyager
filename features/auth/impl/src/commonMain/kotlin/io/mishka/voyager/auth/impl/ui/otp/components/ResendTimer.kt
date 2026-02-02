package io.mishka.voyager.auth.impl.ui.otp.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import voyager.features.auth.impl.generated.resources.Res
import voyager.features.auth.impl.generated.resources.otp_resend_in
import voyager.features.auth.impl.generated.resources.otp_send_again
import java.util.Locale

private const val MILLS_IN_SEC = 1000L
private const val SEC_IN_MIN = 60L
private const val MILLS_IN_MINUTE = SEC_IN_MIN * MILLS_IN_SEC

@Composable
internal fun ResendTimer(
    resendCode: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentTimeMills by remember {
        mutableLongStateOf(MILLS_IN_MINUTE)
    }
    var isTimerRunning by remember {
        mutableStateOf(true)
    }
    val startTimer = {
        currentTimeMills = MILLS_IN_MINUTE
        isTimerRunning = true
    }

    LaunchedEffect(currentTimeMills, isTimerRunning) {
        when {
            // Still count down
            currentTimeMills > 0 && isTimerRunning -> {
                delay(MILLS_IN_SEC)
                currentTimeMills -= MILLS_IN_SEC
            }
            // Reset timer
            isTimerRunning -> {
                isTimerRunning = false
            }
        }
    }

    val timeToWait = remember(currentTimeMills) {
        derivedStateOf {
            val defaultLocale = Locale.getDefault()
            val minutes = String.format(defaultLocale, "%02d", currentTimeMills / MILLS_IN_MINUTE)
            val sec = String.format(defaultLocale, "%02d", currentTimeMills / MILLS_IN_SEC % SEC_IN_MIN)

            "$minutes:$sec"
        }
    }

    val textColor = animateColorAsState(
        targetValue = if (isTimerRunning) {
            VoyagerTheme.colors.subtext
        } else {
            VoyagerTheme.colors.font
        }
    )

    Text(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = !isTimerRunning) {
                resendCode()
                startTimer()
            },
        text = if (isTimerRunning) {
            stringResource(Res.string.otp_resend_in, timeToWait.value)
        } else {
            stringResource(Res.string.otp_send_again)
        },
        style = VoyagerTheme.typography.bodyBold,
        textAlign = TextAlign.Start,
        color = textColor.value,
    )
}
