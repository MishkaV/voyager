package io.mishka.voyager.features.main.impl.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import io.mishkav.voyager.core.ui.theme.VoyagerIcon
import io.mishkav.voyager.core.ui.theme.icons.profile24
import io.mishkav.voyager.core.ui.theme.icons.search24
import io.mishkav.voyager.core.ui.theme.icons.world24

enum class MainBottomTab {
    HOME,
    SEARCH,
    PROFILE;

    val icon: ImageVector
        get() = when (this) {
            HOME -> VoyagerIcon.world24
            SEARCH -> VoyagerIcon.search24
            PROFILE -> VoyagerIcon.profile24
        }
}
