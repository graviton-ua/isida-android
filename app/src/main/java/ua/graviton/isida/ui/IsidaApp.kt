package ua.graviton.isida.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ua.graviton.isida.domain.SystemBarColorManager
import ua.graviton.isida.ui.theme.IsidaTheme

@Composable
fun IsidaApp(
    onConnectDevice: () -> Unit,
    onDisconnectDevice: () -> Unit,
) {
    IsidaTheme {
        ProvideWindowInsets {
            SystemStatusBarColor()
            AppNavigation(
                onConnectDevice = onConnectDevice,
                onDisconnectDevice = onDisconnectDevice,
            )
        }
    }
}

@Composable
private fun SystemStatusBarColor(
    systemUiController: SystemUiController = rememberSystemUiController()
) {
    // This code moved to separate composable to prevent recomposition of #AppNavigation each time #darkIconsState changed
    val darkIconsState by SystemBarColorManager.darkIcons.collectAsState() //remember { mutableStateOf(true) }
    // Launch setSystemBarsColor each time darkIconsState changed
    LaunchedEffect(darkIconsState) {
        systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIconsState)
    }
}