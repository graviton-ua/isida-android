package ua.graviton.isida.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import co.touchlab.kermit.Logger
import com.whoppah.metrox.viewmodel.LocalViewModelFactoryOwner
import com.whoppah.metrox.viewmodel.ViewModelFactoryOwner
import kotlinx.coroutines.flow.MutableStateFlow
import ua.graviton.isida.App
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

        splashScreen.setKeepOnScreenCondition { splashScreenKeep.value }

        setContent {
            CompositionLocalProvider(
                // Provide a way to access the ViewModel factory to injectedViewModel calls down the composable tree
                LocalViewModelFactoryOwner provides object : ViewModelFactoryOwner {
                    override val viewModelFactory: ViewModelProvider.Factory get() = appGraph.viewModelFactory
                },
            ) {
                IsidaApp()
            }
        }
    }
}