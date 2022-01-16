package ua.graviton.isida.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import ua.graviton.isida.ui.devicemode.DeviceModeDialog
import ua.graviton.isida.ui.home.HomeScreen
import ua.graviton.isida.ui.setprop.SetPropDialog

private sealed class Screen(val route: String) {
    object Auth : Screen("auth_root")
    object Main : Screen("main_root")
}

private sealed class LeafScreen(open val route: String) {
    open fun createRoute(root: Screen) = "${root.route}/$route"

    object Main : LeafScreen("main")
    object PowerDialog : LeafScreen("power_dialog")
    object SetPropDialog : LeafScreen("set_prop/{id}") {
        fun createRoute(root: Screen, id: String): String = "${root.route}/set_prop/$id"
    }
}

@Composable
internal fun AppNavigation(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {

        addMainNavFlowTopLevel(
            navController,
        )
    }
}


private fun NavGraphBuilder.addMainNavFlowTopLevel(
    navController: NavController,
) {
    navigation(
        route = Screen.Main.route,
        startDestination = LeafScreen.Main.createRoute(Screen.Main)
    ) {
        addMain(navController, Screen.Main)
        addPowerDialog(navController, Screen.Main)
        addSetPropDialog(navController, Screen.Main)
    }
}


private fun NavGraphBuilder.addMain(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.Main.createRoute(root)) {
        HomeScreen(
            openPowerDialog = { navController.navigate(LeafScreen.PowerDialog.createRoute(root)) },
            openSetPropDialog = { navController.navigate(LeafScreen.SetPropDialog.createRoute(root, it)) },
        )
    }
}

private fun NavGraphBuilder.addPowerDialog(
    navController: NavController,
    root: Screen,
) {
    dialog(LeafScreen.PowerDialog.createRoute(root)) {
        DeviceModeDialog(
            navigateUp = { navController.navigateUp() }
        )
    }
}

private fun NavGraphBuilder.addSetPropDialog(
    navController: NavController,
    root: Screen,
) {
    dialog(
        route = LeafScreen.SetPropDialog.createRoute(root),
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) {
        SetPropDialog(
            navigateUp = { navController.navigateUp() }
        )
    }
}