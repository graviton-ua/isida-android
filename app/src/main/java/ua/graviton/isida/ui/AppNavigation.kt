package ua.graviton.isida.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import ua.graviton.isida.ui.home.HomeScreen

private sealed class Screen(val route: String) {
    object Auth : Screen("auth_root")
    object Main : Screen("main_root")
}

private sealed class LeafScreen(open val route: String) {
    open fun createRoute(root: Screen) = "${root.route}/$route"

    object Main : LeafScreen("main")
}

@Composable
internal fun AppNavigation(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    onConnectDevice: () -> Unit,
    onDisconnectDevice: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {

        addMainNavFlowTopLevel(
            navController,
            onConnectDevice = onConnectDevice,
            onDisconnectDevice = onDisconnectDevice,
        )
    }
}


private fun NavGraphBuilder.addMainNavFlowTopLevel(
    navController: NavController,
    onConnectDevice: () -> Unit,
    onDisconnectDevice: () -> Unit,
) {
    navigation(
        route = Screen.Main.route,
        startDestination = LeafScreen.Main.createRoute(Screen.Main)
    ) {
        addMain(
            navController, Screen.Main,
            onConnectDevice = onConnectDevice,
            onDisconnectDevice = onDisconnectDevice,
        )
    }
}


private fun NavGraphBuilder.addMain(
    navController: NavController,
    root: Screen,
    onConnectDevice: () -> Unit,
    onDisconnectDevice: () -> Unit,
) {
    composable(LeafScreen.Main.createRoute(root)) {
        HomeScreen(
            onConnectDevice = onConnectDevice,
            onDisconnectDevice = onDisconnectDevice,
        )
    }
}