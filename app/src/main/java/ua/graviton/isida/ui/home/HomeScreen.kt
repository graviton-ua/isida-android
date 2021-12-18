package ua.graviton.isida.ui.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.SettingsApplications
import androidx.compose.material.icons.outlined.Summarize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
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
import com.google.accompanist.insets.ui.TopAppBar
import timber.log.Timber
import ua.graviton.isida.R
import ua.graviton.isida.domain.SystemBarColorManager
import ua.graviton.isida.domain.services.intentBLServiceConnectDevice
import ua.graviton.isida.domain.services.intentBLServiceDisconnectDevice
import ua.graviton.isida.ui.contracts.ScanForDeviceResultContract
import ua.graviton.isida.ui.utils.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    openPowerDialog: () -> Unit,
) {
    LaunchedEffect(Unit) { SystemBarColorManager.darkIcons.value = true }

    val context = LocalContext.current

    val scanForDevice = rememberLauncherForActivityResult(ScanForDeviceResultContract()) { address ->
        Timber.d("Selected device: $address | start service")
        if (address != null) with(context) { startService(intentBLServiceConnectDevice(address)) }
    }

    HomeScreen(
        viewModel = hiltViewModel(),
        connectDevice = { scanForDevice.launch(Unit) },
        disconnectDevice = { with(context) { startService(intentBLServiceDisconnectDevice()) } },
        openPowerDialog = openPowerDialog,
    )
}

@Composable
private fun HomeScreen(
    viewModel: HomeViewModel,
    connectDevice: () -> Unit,
    disconnectDevice: () -> Unit,
    openPowerDialog: () -> Unit,
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(viewState) { action ->
        when (action) {
            //is ShopCartAction.Close -> navigateUp()
            is HomeAction.ConnectDevice -> connectDevice()
            is HomeAction.DisconnectDevice -> disconnectDevice()
            is HomeAction.OpenPowerDialog -> openPowerDialog()
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
        topBar = {
            HomeTopBar(
                deviceConnected = state.deviceConnected,
                modifier = Modifier.fillMaxWidth(),
                connectDevice = { actioner(HomeAction.ConnectDevice) },
                disconnectDevice = { actioner(HomeAction.DisconnectDevice) },
                openPowerDialog = { actioner(HomeAction.OpenPowerDialog) },
            )
        },
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
    ) { paddings ->
        HomeNavigation(
            navController = navController,
            modifier = Modifier.padding(paddings)
        )
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
private fun HomeTopBar(
    deviceConnected: Boolean,
    modifier: Modifier = Modifier,
    connectDevice: () -> Unit,
    disconnectDevice: () -> Unit,
    openPowerDialog: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            if (deviceConnected)
                TextButton(onClick = { openPowerDialog() }) {
                    Icon(imageVector = Icons.Default.Flag, contentDescription = "Device menu")
                    Text(text = "Power")
                }
            IconButton(onClick = { expanded = !expanded }) { Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Device menu") }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                when (deviceConnected) {
                    false -> DropdownMenuItem(
                        onClick = {
                            connectDevice()
                            expanded = false
                        }
                    ) { Text(text = "Connect") }
                    true -> DropdownMenuItem(
                        onClick = {
                            disconnectDevice()
                            expanded = false
                        }
                    ) { Text(text = "Disconnect") }
                }
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = contentColorFor(MaterialTheme.colors.surface),
        contentPadding = rememberInsetsPaddingValues(insets = LocalWindowInsets.current.statusBars),
        modifier = modifier
    )
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