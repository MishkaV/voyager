package io.mishka.voyager.home.impl.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mapbox.common.MapboxOptions.accessToken
import com.mapbox.maps.CameraBoundsOptions
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.plugin.locationcomponent.location
import voyager.features.home.impl.BuildKonfig

private const val MAX_ZOOM = 6.0
private const val FIRST_ZOOM = 3.0
private const val FIRST_ZOOM_ANIMATION_MILLS = 3500L

@Composable
internal actual fun MapboxMapView(
    viewModel: HomeViewModel,
    modifier: Modifier
) {
    val mapViewportState = rememberMapViewportState {
        accessToken = BuildKonfig.MAPBOX_TOKEN
        setCameraOptions {
            zoom(1.0)
        }
    }

    MapboxMap(
        modifier = modifier,
        mapViewportState = mapViewportState,
        scaleBar = {
            // Not provide
        },
        compass = {
            Compass(alignment = Alignment.CenterEnd)
        },
        logo = {
            // Not provide
        },
        onMapClickListener = { point ->
            // Get country by coordinates when user clicks on the map
            viewModel.selectCountryByCoordinates(
                latitude = point.latitude(),
                longitude = point.longitude()
            )
            true
        }
    ) {
        MapEffect(Unit) { mapView ->
            mapView.mapboxMap.setBounds(
                CameraBoundsOptions.Builder()
                    .maxZoom(MAX_ZOOM)
                    .build()
            )

            mapView.location.updateSettings {
                enabled = true
                puckBearingEnabled = false
            }

            // Move to user location
            mapView.location.addOnIndicatorPositionChangedListener { point ->
                mapView.mapboxMap.easeTo(
                    CameraOptions.Builder()
                        .center(point)
                        .zoom(FIRST_ZOOM)
                        .build(),
                    MapAnimationOptions.mapAnimationOptions {
                        duration(FIRST_ZOOM_ANIMATION_MILLS)
                    }
                )
            }
        }
    }
}
