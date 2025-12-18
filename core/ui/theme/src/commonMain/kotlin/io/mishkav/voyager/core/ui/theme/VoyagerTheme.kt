package io.mishkav.voyager.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable

@Immutable
object VoyagerTheme {

    val colors: VoyagerColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: VoyagerTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val icons: VoyagerIcon
        @Composable
        @ReadOnlyComposable
        get() = LocaleIcon.current
}

@Composable
fun VoyagerTheme(
    colors: VoyagerColor = VoyagerTheme.colors,
    typography: VoyagerTypography? = null,
    icons: VoyagerIcon = VoyagerTheme.icons,
    content: @Composable () -> Unit,
) {
    val finalTypography = typography ?: VoyagerTypography(voyagerFontFamily())

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides finalTypography,
        LocaleIcon provides icons,
    ) {
        content()
    }
}
