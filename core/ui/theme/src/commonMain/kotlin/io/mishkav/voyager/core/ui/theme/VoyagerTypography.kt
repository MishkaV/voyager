package io.mishkav.voyager.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import voyager.core.ui.theme.generated.resources.Res
import voyager.core.ui.theme.generated.resources.rubik_black
import voyager.core.ui.theme.generated.resources.rubik_bold
import voyager.core.ui.theme.generated.resources.rubik_extrabold
import voyager.core.ui.theme.generated.resources.rubik_light
import voyager.core.ui.theme.generated.resources.rubik_medium
import voyager.core.ui.theme.generated.resources.rubik_regular
import voyager.core.ui.theme.generated.resources.rubik_semibold

val LocalTypography = staticCompositionLocalOf { VoyagerTypography() }

@Composable
internal fun voyagerFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.rubik_regular, FontWeight.Normal, FontStyle.Normal),
        Font(Res.font.rubik_light, FontWeight.Light, FontStyle.Normal),
        Font(Res.font.rubik_medium, FontWeight.Medium, FontStyle.Normal),
        Font(Res.font.rubik_semibold, FontWeight.SemiBold, FontStyle.Normal),
        Font(Res.font.rubik_bold, FontWeight.Bold, FontStyle.Normal),
        Font(Res.font.rubik_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
        Font(Res.font.rubik_black, FontWeight.Black, FontStyle.Normal),
    )
}

@Immutable
class VoyagerTypography(
    private val fontFamily: FontFamily? = null,
) {
    val h1: TextStyle = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontFamily = fontFamily,
        fontSize = 30.sp,
        lineHeight = 36.sp,
    )

    val h2: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = fontFamily,
        fontSize = 20.sp,
        lineHeight = 24.sp,
    )

    val h3: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = fontFamily,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    )

    val bodyBold: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontFamily = fontFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    )

    val body: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = fontFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    )

    val captionBold: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontFamily = fontFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    )

    val caption: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = fontFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    )

    val buttonXL: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontFamily = fontFamily,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    )

    val buttonL: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontFamily = fontFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    )
}
