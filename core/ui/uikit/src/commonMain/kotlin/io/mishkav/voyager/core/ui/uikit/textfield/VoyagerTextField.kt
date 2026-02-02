package io.mishkav.voyager.core.ui.uikit.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.LocalColors
import io.mishkav.voyager.core.ui.theme.LocalTypography
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.theme.icons.close24

@Composable
fun VoyagerTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.SingleLine,
) {
    val colors = LocalColors.current
    val typography = LocalTypography.current

    val isTextFieldEmpty = remember {
        derivedStateOf { state.text.isNotEmpty() }
    }

    TextField(
        state = state,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = typography.buttonL,
        label = label?.let {
            {
                Text(text = label)
            }
        },
        placeholder = placeholder?.let {
            {
                Text(text = placeholder)
            }
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = isTextFieldEmpty.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(
                    modifier = Modifier.clickable { state.clearText() },
                    imageVector = VoyagerTheme.icons.close24,
                    contentDescription = "Clear",
                    tint = colors.subtext,
                )
            }
        },
        lineLimits = lineLimits,
        isError = isError,
        inputTransformation = inputTransformation,
        outputTransformation = outputTransformation,
        keyboardOptions = keyboardOptions,
        contentPadding = OutlinedTextFieldDefaults.contentPadding(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 16.dp,
        ),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = colors.black,
            unfocusedTextColor = colors.black,
            disabledTextColor = colors.black,
            errorTextColor = colors.black,

            focusedContainerColor = colors.white,
            unfocusedContainerColor = colors.white,
            disabledContainerColor = colors.white,
            errorContainerColor = colors.white,

            cursorColor = colors.brand,
            errorCursorColor = colors.brand,

            focusedBorderColor = colors.white,
            unfocusedBorderColor = colors.white,
            disabledBorderColor = colors.white,
            errorBorderColor = colors.white,

            focusedLabelColor = colors.brand,
            unfocusedLabelColor = colors.subtext,
            disabledLabelColor = colors.disabled,
            errorLabelColor = colors.brand,

            focusedTrailingIconColor = colors.subtext,
            unfocusedTrailingIconColor = colors.subtext,
            disabledTrailingIconColor = colors.disabled,
            errorTrailingIconColor = colors.subtext,
        ),
    )
}
