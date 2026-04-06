package ua.isida

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import dev.zacsweers.metro.createGraphFactory
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import kotlinx.coroutines.cancel
import ua.isida.base.PlatformConfig
import ua.isida.base.PlatformInfo
import ua.isida.shared.di.JvmAppGraph

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
            LocalMetroViewModelFactory provides appGraph.metroViewModelFactory,
        ) {
            App(
                state = rememberAppState(exitApp = { appGraph.appScope.cancel(); exitApplication() })
            )
        }
    }
}