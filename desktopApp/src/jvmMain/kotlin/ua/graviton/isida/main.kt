package ua.graviton.isida

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import dev.zacsweers.metro.createGraphFactory
import kotlinx.coroutines.cancel
import ua.graviton.isida.shared.di.JvmAppGraph
import ua.isida.base.PlatformConfig
import ua.isida.base.PlatformInfo
import ua.isida.metrox.viewmodel.LocalMetroViewModelFactory

fun main() {
    //System.setProperty("skiko.renderApi", "OPENGL") //TODO: Fixes issue with G-Sync stuttering

    val platformConfig = PlatformConfig(
        isDebug = true,
        isQaBuild = true,
        crashReporting = false,
        platformInfo = PlatformInfo(
            userDevice = "",
            osVersion = "",
            appIdentifier = "jvm"
        ),
    )

    // Create an injection graph
    val appGraph = createGraphFactory<JvmAppGraph.Factory>().create(platformConfig)
    appGraph.initializers.init()

    application {
        CompositionLocalProvider(
            // Provide a way to access the ViewModel factory to injectedViewModel calls down the composable tree
            LocalMetroViewModelFactory provides appGraph.viewModelFactory,
        ) {
            App(
                state = rememberAppState(exitApp = { appGraph.appScope.cancel(); exitApplication() })
            )
        }
    }
}