package ua.graviton.isida.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.utils.composable
import com.ramcosta.composedestinations.utils.dialogComposable
import ua.graviton.isida.ui.destinations.DeviceModeDialogDestination
import ua.graviton.isida.ui.destinations.HomeScreenDestination
import ua.graviton.isida.ui.destinations.SetPropDialogDestination
import ua.graviton.isida.ui.devicemode.DeviceModeDialog
import ua.graviton.isida.ui.home.HomeScreen
import ua.graviton.isida.ui.setprop.SetPropDialog

@Composable
internal fun AppNavigation(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreenDestination.route,
    ) {
        addMain(navController)
        addDeviceModeDialog(navController)
        addSetPropDialog(navController)
    }
}


private fun NavGraphBuilder.addMain(
    navController: NavController,
) {
    composable(HomeScreenDestination) {
        HomeScreen(
            openPowerDialog = { navController.navigate(DeviceModeDialogDestination) },
            openSetPropDialog = { navController.navigate(SetPropDialogDestination(id = it)) },
        )
    }
}

private fun NavGraphBuilder.addDeviceModeDialog(
    navController: NavController,
) {
    dialogComposable(DeviceModeDialogDestination) {
        DeviceModeDialog(
            navigateUp = { navController.navigateUp() }
        )
    }
}

private fun NavGraphBuilder.addSetPropDialog(
    navController: NavController,
) {
    dialogComposable(SetPropDialogDestination) {
        SetPropDialog(
            navigateUp = { navController.navigateUp() }
        )
    }
}