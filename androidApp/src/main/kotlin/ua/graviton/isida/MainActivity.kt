package ua.graviton.isida

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import co.touchlab.kermit.Logger
import com.whoppah.metrox.viewmodel.LocalMetroViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ua.graviton.isida.data.bluetooth.ConnectionState
import ua.graviton.isida.domain.services.intentBLConnectionService
import ua.graviton.isida.shared.ui.IsidaApp

fun Context.intentMain() = Intent(this, MainActivity::class.java)

class MainActivity : ComponentActivity() {
    private val logger by lazy { Logger.withTag("MainActivity") }
    private val splashScreenKeep = MutableStateFlow(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val appGraph = (application as App).appGraph

        // --- ADDED: Android Service Orchestration ---
        // Observe connection state to start/stop the Android Service which handles Notifications
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                appGraph.connectionManager.connectionState.collect { state ->
                    if (state == ConnectionState.CONNECTING || state == ConnectionState.CONNECTED) {
                        try {
                            val intent = intentBLConnectionService()
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                startForegroundService(intent)
                            } else {
                                startService(intent)
                            }
                        } catch (e: Exception) {
                            logger.e(e) { "Failed to start Bluetooth Service" }
                        }
                    }
                    // Service handles its own stopping when it observes DISCONNECTED
                }
            }
        }
        // --------------------------------------------

        splashScreen.setKeepOnScreenCondition { splashScreenKeep.value }

        setContent {
            CompositionLocalProvider(
                // Provide a way to access the ViewModel factory to injectedViewModel calls down the composable tree
                LocalMetroViewModelFactory provides appGraph.viewModelFactory,
            ) {
                IsidaApp()
            }
        }
    }
}