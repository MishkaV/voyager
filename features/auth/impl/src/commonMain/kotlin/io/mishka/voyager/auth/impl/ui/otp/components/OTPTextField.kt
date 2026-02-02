package io.mishka.voyager.auth.impl.ui.otp.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme

internal const val MAX_OTP_LENGTH = 6

@Composable
internal fun OTPTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(
        state = state,
        modifier = modifier
            .heightIn(64.dp)
            .focusRequester(focusRequester),
        inputTransformation = InputTransformation.maxLength(MAX_OTP_LENGTH),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        lineLimits = TextFieldLineLimits.SingleLine,
        decorator = {
            val otpCode = state.text.toString()
            val activeDigitIndex = otpCode.length.coerceAtMost(MAX_OTP_LENGTH - 1)

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(MAX_OTP_LENGTH) { index ->
                    val isActive = remember(activeDigitIndex) {
                        derivedStateOf {
                            index == activeDigitIndex
                        }
                    }

                    DigitBox(
                        number = otpCode.getOrElse(index, { ' ' }),
                        isActive = isActive.value,
                    )
                }
            }
        }
    )
}

@Composable
private fun DigitBox(
    number: Char,
    isActive: Boolean,
    modifier: Modifier = Modifier,
) {
    val colors = VoyagerTheme.colors

    val sharedShape = RoundedCornerShape(12.dp)
    val borderColor = animateColorAsState(
        targetValue = if (isActive) colors.brand else colors.border
    )
    val charColor = animateColorAsState(
        targetValue = if (isActive) colors.brand else colors.subtext
    )

    Box(
        modifier = modifier
            .clip(sharedShape)
            .border(
                width = 1.dp,
                color = borderColor.value,
                shape = sharedShape,
            )
            .animateContentSize()
            .width(if (isActive) 44.dp else 40.dp)
            .height(if (isActive) 64.dp else 52.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = number.toString(),
            style = VoyagerTheme.typography.h2.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp,
                lineHeight = 32.sp,
            ),
            textAlign = TextAlign.Center,
            color = charColor.value,
        )
    }
}
