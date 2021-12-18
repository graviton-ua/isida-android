package ua.graviton.isida.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ua.graviton.isida.ui.home.prop.PropScreen
import ua.graviton.isida.ui.home.report.ReportScreen
import ua.graviton.isida.ui.home.stats.StatsScreen

sealed class HomeNavScreen(val route: String) {
    object Stats : HomeNavScreen("stats")
    object Prop : HomeNavScreen("prop")
    object Report : HomeNavScreen("report")
}

@Composable
internal fun HomeNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = HomeNavScreen.Stats.route,
        modifier = modifier,
    ) {

        addStats(navController)
        addProp(navController)
        addReport(navController)
    }
}

private fun NavGraphBuilder.addStats(
    navController: NavController,
) {
    composable(HomeNavScreen.Stats.route) {
        StatsScreen()
    }
}

private fun NavGraphBuilder.addProp(
    navController: NavController,
) {
    composable(HomeNavScreen.Prop.route) {
        PropScreen()
    }
}

private fun NavGraphBuilder.addReport(
    navController: NavController,
) {
    composable(HomeNavScreen.Report.route) {
        ReportScreen()
    }
}