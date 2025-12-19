package io.mishkav.voyager.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalColors = staticCompositionLocalOf<VoyagerColor> { DefaultVoyagerColor }

@Immutable
interface VoyagerColor {

    val brand: Color

    val font: Color

    val white: Color

    val subtext: Color

    val disabled: Color

    val border: Color

    val black: Color

    val background: Color
}

@Suppress("MagicNumber")
object DefaultVoyagerColor : VoyagerColor {
    override val brand: Color = Color(0xFF590004)

    override val font: Color = Color(0xFFFAFAFA)

    override val white: Color = Color(0xFFFFFFFF)

    override val subtext: Color = Color(0xFFA3A3A3)

    override val disabled: Color = Color(0xFFB2B2B2)

    override val border: Color = Color(0xFF404040)

    override val black: Color = Color(0xFF000000)

    override val background: Color = Color(0xFF0A0A0A)
}
