package ua.graviton.isida

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import androidx.lifecycle.ViewModelProvider
import com.whoppah.base.PlatformConfig
import com.whoppah.base.PlatformInfo
import com.whoppah.metrox.viewmodel.LocalViewModelFactoryOwner
import com.whoppah.metrox.viewmodel.ViewModelFactoryOwner
import dev.zacsweers.metro.createGraphFactory
import kotlinx.coroutines.cancel
import ua.graviton.isida.shared.di.JvmAppGraph

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
            LocalViewModelFactoryOwner provides object : ViewModelFactoryOwner {
                override val viewModelFactory: ViewModelProvider.Factory get() = appGraph.viewModelFactory
            },
        ) {
            App(
                state = rememberAppState(exitApp = { appGraph.appScope.cancel(); exitApplication() })
            )
        }
    }
}