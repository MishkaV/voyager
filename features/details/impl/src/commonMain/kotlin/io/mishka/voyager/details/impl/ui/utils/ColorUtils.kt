package io.mishka.voyager.details.impl.ui.utils

import androidx.compose.ui.graphics.Color

private const val BASE_LENGTH = 8

internal fun String.toComposeColor(): Color {
    val cleaned = removePrefix("#")
    val colorLong = cleaned.toLong(16)

    return if (cleaned.length == BASE_LENGTH) {
        Color(colorLong)
    } else {
        Color(0xFF000000 or colorLong)
    }
}
