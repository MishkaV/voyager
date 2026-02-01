package io.mishka.voyager

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.essenty.backhandler.BackHandler
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.android.ActivityKey
import io.mishkav.voyager.features.navigation.api.RootComponent
import io.mishkav.voyager.features.navigation.api.model.VoyagerStartupStatus
import io.mishkav.voyager.features.navigation.impl.ui.RootComposePoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ContributesIntoMap(AppScope::class, binding<Activity>())
@ActivityKey(MainActivity::class)
@Inject
class MainActivity : ComponentActivity() {

    @Inject
    private lateinit var rootComponentFactory: RootComponent.Factory

    @Inject
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Start check startup status
        viewModel.init(intent)

        var rootComponent by mutableStateOf<RootComponent?>(null)
        var showSplash by mutableStateOf(true)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.startupStatus
                    .onEach { updatedStatus ->
                        val isLoading = updatedStatus == VoyagerStartupStatus.Loading
                        val isRootComponentInitialized = rootComponent != null

                        if (!isLoading && !isRootComponentInitialized) {
                            // Setup decompose
                            rootComponent = rootComponentFactory.create(
                                componentContext = defaultComponentContext(),
                                backHandler = BackHandler(onBackPressedDispatcher),
                                startupStatus = updatedStatus,
                            )
                        }

                        // Decide show splash or not
                        showSplash = updatedStatus == VoyagerStartupStatus.Loading
                    }
                    .collect()
            }
        }

        splashScreen.setKeepOnScreenCondition { showSplash }

        setContent {
            rootComponent?.let { root ->
                RootComposePoint(
                    root = root,
                )
            }
        }
    }
}
