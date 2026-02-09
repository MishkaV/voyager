package io.mishka.voyager.details.impl.ui.details.blocks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.image.VoyagerSharedImage
import io.mishkav.voyager.core.ui.uikit.transition.LocalSharedTransitionScope
import io.mishkav.voyager.core.utils.supabase.voyagerAuthenticatedStorageItem

internal fun LazyListScope.titleBlock(
    countryName: String,
    flagFullPatch: String,
) {
    item(
        key = "TITLE_KEY",
        contentType = "TITLE_CONTENT_TYPE"
    ) {
        TitleBlock(
            countryName = countryName,
            flagFullPatch = flagFullPatch,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun TitleBlock(
    countryName: String,
    flagFullPatch: String,
    modifier: Modifier = Modifier,
) {
    val sharedScope = LocalSharedTransitionScope.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        with(sharedScope) {
            VoyagerSharedImage(
                modifier = Modifier.size(96.dp),
                data = voyagerAuthenticatedStorageItem(flagFullPatch),
                contentDescription = null,
                shareKey = "country_$countryName",
                shape = CircleShape,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(30.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = countryName,
            style = VoyagerTheme.typography.h1,
            color = VoyagerTheme.colors.font,
            textAlign = TextAlign.Center,
        )
    }
}
