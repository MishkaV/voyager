package io.mishka.voyager.location.impl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.appbar.SimpleVoyagerAppBar
import io.mishkav.voyager.core.ui.uikit.button.VoyagerButton
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonSizes
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonStyles
import io.mishkav.voyager.core.utils.permissions.impl.VoyagerPickerSource
import io.mishkav.voyager.core.utils.permissions.impl.actionWithPermission
import io.mishkav.voyager.core.utils.permissions.impl.rememberVoyagerPermissionHandler
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import voyager.features.location.impl.generated.resources.Res
import voyager.features.location.impl.generated.resources.location
import voyager.features.location.impl.generated.resources.location_button_enable
import voyager.features.location.impl.generated.resources.location_button_not_now
import voyager.features.location.impl.generated.resources.location_description
import voyager.features.location.impl.generated.resources.location_title

@Composable
fun LocationScreen(
    onRequestLocation: (isGranted: Boolean) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val permissionHandler = rememberVoyagerPermissionHandler()
    val scope = rememberCoroutineScope()

    LocationScreenContent(
        requestLocationPermission = {
            scope.actionWithPermission(
                pickerSource = VoyagerPickerSource.LOCATION,
                permissionHandler = permissionHandler,
                onGranted = { onRequestLocation(true) },
                onAlwaysDenied = { onRequestLocation(false) }
            )
        },
        denyLocationRequest = { onRequestLocation(false) },
        onBack = onBack,
        modifier = modifier,
    )
}

@Composable
fun LocationScreenContent(
    requestLocationPermission: () -> Unit,
    denyLocationRequest: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val topSpaceWeight = 0.32f

    Column(
        modifier = modifier
            .background(VoyagerTheme.colors.background)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SimpleVoyagerAppBar(onBack = onBack)

        Spacer(Modifier.weight(topSpaceWeight))

        Image(
            modifier = Modifier.size(170.dp),
            painter = painterResource(Res.drawable.location),
            contentDescription = null,
        )

        Spacer(Modifier.height(64.dp))

        Text(
            text = stringResource(Res.string.location_title),
            style = VoyagerTheme.typography.h1,
            color = VoyagerTheme.colors.font,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(Res.string.location_description),
            style = VoyagerTheme.typography.bodyBold,
            color = VoyagerTheme.colors.subtext,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.weight(1 - topSpaceWeight))

        VoyagerButton(
            modifier = Modifier.fillMaxWidth(),
            style = VoyagerDefaultButtonStyles.primary(),
            size = VoyagerDefaultButtonSizes.buttonXL(),
            text = stringResource(Res.string.location_button_enable),
            onClick = requestLocationPermission,
        )

        Spacer(Modifier.height(14.dp))

        VoyagerButton(
            modifier = Modifier.fillMaxWidth(),
            style = VoyagerDefaultButtonStyles.custom(
                containerColor = VoyagerTheme.colors.border,
                contentColor = VoyagerTheme.colors.font,
            ),
            size = VoyagerDefaultButtonSizes.buttonXL(),
            text = stringResource(Res.string.location_button_not_now),
            onClick = denyLocationRequest,
        )

        Spacer(Modifier.height(12.dp))
    }
}
