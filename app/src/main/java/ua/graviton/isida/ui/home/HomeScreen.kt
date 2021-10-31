package ua.graviton.isida.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.SettingsApplications
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.SettingsApplications
import androidx.compose.material.icons.outlined.Summarize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.BottomNavigation
import ua.graviton.isida.R
import ua.graviton.isida.ui.utils.collectAsStateWithLifecycle

@Composable
fun HomeScreen() {
    //LaunchedEffect("once") { SystemBarColorManager.darkIcons.value = true }

    HomeScreen(
        viewModel = hiltViewModel(),
    )
}

@Composable
private fun HomeScreen(
    viewModel: HomeViewModel,
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(viewState) { action ->
        when (action) {
            //is ShopCartAction.Close -> navigateUp()
            //is ShopCartAction.NavigateCheckout -> openCheckout()
            else -> viewModel.submitAction(action)
        }
    }
}

@Composable
private fun HomeScreen(
    state: HomeViewState,
    actioner: (HomeAction) -> Unit,
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val currentSelectedItem by navController.currentScreenAsState()
            HomeBottomNavigation(
                selectedNavigation = currentSelectedItem,
                onNavigationSelected = { selected ->
                    navController.navigate(selected.route) {
                        launchSingleTop = true
                        restoreState = true

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        HomeNavigation(navController = navController)
    }
}


/**
 * Adds an [NavController.OnDestinationChangedListener] to this [NavController] and updates the
 * returned [State] which is updated as the destination changes.
 */
@Stable
@Composable
private fun NavController.currentScreenAsState(): State<HomeNavScreen> {
    val selectedItem = remember { mutableStateOf<HomeNavScreen>(HomeNavScreen.Stats) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == HomeNavScreen.Stats.route } -> {
                    selectedItem.value = HomeNavScreen.Stats
                }
                destination.hierarchy.any { it.route == HomeNavScreen.Prop.route } -> {
                    selectedItem.value = HomeNavScreen.Prop
                }
                destination.hierarchy.any { it.route == HomeNavScreen.Report.route } -> {
                    selectedItem.value = HomeNavScreen.Report
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}

@Composable
private fun HomeBottomNavigation(
    selectedNavigation: HomeNavScreen,
    onNavigationSelected: (HomeNavScreen) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = contentColorFor(MaterialTheme.colors.surface),
        contentPadding = rememberInsetsPaddingValues(LocalWindowInsets.current.navigationBars),
        modifier = modifier
    ) {
        HomeNavigationItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    HomeNavigationItemIcon(
                        item = item,
                        selected = selectedNavigation == item.screen
                    )
                },
                label = { Text(text = stringResource(item.labelResId)) },
                selected = selectedNavigation == item.screen,
                onClick = { onNavigationSelected(item.screen) },
            )
        }
    }
}

@Composable
private fun HomeNavigationItemIcon(item: HomeNavigationItem, selected: Boolean) {
    val painter = when (item) {
        is HomeNavigationItem.ResourceIcon -> painterResource(item.iconResId)
        is HomeNavigationItem.ImageVectorIcon -> rememberVectorPainter(item.iconImageVector)
    }
    val selectedPainter = when (item) {
        is HomeNavigationItem.ResourceIcon -> item.selectedIconResId?.let { painterResource(it) }
        is HomeNavigationItem.ImageVectorIcon -> item.selectedImageVector?.let { rememberVectorPainter(it) }
    }

    if (selectedPainter != null) {
        Crossfade(targetState = selected) {
            Icon(
                painter = if (it) selectedPainter else painter,
                contentDescription = stringResource(item.contentDescriptionResId),
            )
        }
    } else {
        Icon(
            painter = painter,
            contentDescription = stringResource(item.contentDescriptionResId),
        )
    }
}


private sealed class HomeNavigationItem(
    val screen: HomeNavScreen,
    @StringRes val labelResId: Int,
    @StringRes val contentDescriptionResId: Int,
) {
    class ResourceIcon(
        screen: HomeNavScreen,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        @DrawableRes val iconResId: Int,
        @DrawableRes val selectedIconResId: Int? = null,
    ) : HomeNavigationItem(screen, labelResId, contentDescriptionResId)

    class ImageVectorIcon(
        screen: HomeNavScreen,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        val iconImageVector: ImageVector,
        val selectedImageVector: ImageVector? = null,
    ) : HomeNavigationItem(screen, labelResId, contentDescriptionResId)
}

private val HomeNavigationItems = listOf(
    HomeNavigationItem.ImageVectorIcon(
        screen = HomeNavScreen.Stats,
        labelResId = R.string.home_tab_stats,
        contentDescriptionResId = R.string.home_tab_stats,
        iconImageVector = Icons.Outlined.Analytics,
        selectedImageVector = Icons.Filled.Analytics,
    ),
    HomeNavigationItem.ImageVectorIcon(
        screen = HomeNavScreen.Prop,
        labelResId = R.string.home_tab_prop,
        contentDescriptionResId = R.string.home_tab_prop,
        iconImageVector = Icons.Outlined.SettingsApplications,
        selectedImageVector = Icons.Filled.SettingsApplications,
    ),
    HomeNavigationItem.ImageVectorIcon(
        screen = HomeNavScreen.Report,
        labelResId = R.string.home_tab_report,
        contentDescriptionResId = R.string.home_tab_report,
        iconImageVector = Icons.Outlined.Summarize,
        selectedImageVector = Icons.Filled.Summarize,
    ),
//    HomeNavigationItem.ResourceIcon(
//        screen = HomeNavScreen.Stats,
//        labelResId = R.string.discover_title,
//        contentDescriptionResId = R.string.cd_discover_title,
//        iconResId = R.drawable.ic_weekend_outline,
//        selectedIconResId = R.drawable.ic_weekend_filled,
//    ),
)