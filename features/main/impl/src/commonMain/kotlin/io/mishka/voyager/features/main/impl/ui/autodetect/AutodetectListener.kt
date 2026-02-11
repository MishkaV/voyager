package io.mishka.voyager.features.main.impl.ui.autodetect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.mishka.voyager.features.main.impl.ui.AutodetectState
import io.mishka.voyager.features.main.impl.ui.MainViewModel
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarDuration
import io.mishkav.voyager.core.ui.uikit.snackbar.core.SnackbarMessage
import io.mishkav.voyager.core.utils.permissions.impl.VoyagerPickerSource
import io.mishkav.voyager.core.utils.permissions.impl.actionWithPermission
import io.mishkav.voyager.core.utils.permissions.impl.rememberVoyagerPermissionHandler
import io.mishkav.voyager.features.navigation.api.snackbar.LocalBottomMainSnackbarController
import io.mishkav.voyager.features.navigation.api.snackbar.LocalTopMainSnackbarController
import io.mishkav.voyager.features.navigation.api.snackbar.MainSnackbarState
import kotlinx.coroutines.launch

private const val SNACKBAR_DURATION = 8_000

@Composable
fun AutodetectListener(
    viewModel: MainViewModel,
) {
    val haptic = LocalHapticFeedback.current
    val bottomMainSnackbar = LocalBottomMainSnackbarController.current
    val topSnackbarController = LocalTopMainSnackbarController.current
    val permissionHandler = rememberVoyagerPermissionHandler()
    val scope = rememberCoroutineScope()

    val activeCountry by viewModel.activeCountry.collectAsStateWithLifecycle(initialValue = null)
    val autodetectState by viewModel.autodetectState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        val isLocationGranted = permissionHandler.isPermissionGranted(
            pickerSource = VoyagerPickerSource.LOCATION
        )

        if (isLocationGranted) {
            // Detect current country
            viewModel.detectCurrentCountry()
        } else {
            // Show snackbar requesting permission
            topSnackbarController.show(
                message = SnackbarMessage(
                    duration = SnackbarDuration.Custom(SNACKBAR_DURATION),
                    content = MainSnackbarState.Default(
                        text = "Opps! We missed you permission for autodetect feature",
                        buttonText = "Let's grant it!",
                        onButtonClick = {
                            scope.actionWithPermission(
                                pickerSource = VoyagerPickerSource.LOCATION,
                                permissionHandler = permissionHandler,
                                onGranted = {
                                    topSnackbarController.hide()
                                    scope.launch {
                                        viewModel.detectCurrentCountry()
                                    }
                                },
                                onAlwaysDenied = {
                                    topSnackbarController.hide()
                                }
                            )
                        }
                    ),
                )
            )
        }
    }

    // Show snackbar when country is detected and not visited
    LaunchedEffect(activeCountry, autodetectState) {
        if (autodetectState is AutodetectState.Success && activeCountry != null) {
            val country = activeCountry!!

            if (!country.isVisited) {
                topSnackbarController.show(
                    message = SnackbarMessage(
                        duration = SnackbarDuration.Custom(SNACKBAR_DURATION),
                        content = MainSnackbarState.Default(
                            text = "Hey, are you in ${country.country.name}?",
                            buttonText = "Mark as visited",
                            onButtonClick = {
                                scope.launch {
                                    viewModel.addCountryToVisited(country.country.id)
                                    topSnackbarController.hide()
                                    viewModel.resetAutodetectState()

                                    bottomMainSnackbar.show(
                                        message = SnackbarMessage(
                                            duration = SnackbarDuration.Short,
                                            content = MainSnackbarState.Default(
                                                text = "Passport stamped. ${country.country.name} visited!",
                                                buttonText = "Superb!",
                                            ),
                                        )
                                    )
                                    haptic.performHapticFeedback(HapticFeedbackType.ToggleOn)
                                }
                            }
                        ),
                    )
                )
            } else {
                // Country is already visited, hide snackbar
                topSnackbarController.hide()
                viewModel.resetAutodetectState()
            }
        }
    }
}
