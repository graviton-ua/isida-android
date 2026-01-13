package ua.graviton.isida.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.SettingsApplications
import androidx.compose.material.icons.outlined.Summarize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.resources.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ua.graviton.isida.domain.SystemBarColorManager

@Destination
@Composable
fun HomeScreen(
    openPowerDialog: () -> Unit,
    openSetPropDialog: (String) -> Unit,
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
        openSetPropDialog = openSetPropDialog,
    )
}

@Composable
private fun HomeScreen(
    viewModel: HomeViewModel,
    connectDevice: () -> Unit,
    disconnectDevice: () -> Unit,
    openPowerDialog: () -> Unit,
    openSetPropDialog: (String) -> Unit,
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(viewState) { action ->
        when (action) {
            //is ShopCartAction.Close -> navigateUp()
            is HomeAction.ConnectDevice -> connectDevice()
            is HomeAction.DisconnectDevice -> disconnectDevice()
            is HomeAction.OpenPowerDialog -> openPowerDialog()
            is HomeAction.OpenSetPropDialog -> openSetPropDialog(action.id)
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
            openSetPropDialog = { actioner(HomeAction.OpenSetPropDialog(it)) },
            modifier = Modifier.padding(paddings),
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
    TopAppBar(
        title = { Text(text = stringResource(Res.string.app_name)) },
        actions = {
            var expanded by remember { mutableStateOf(false) }
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
                        text = { Text(text = "Connect") },
                        onClick = { connectDevice(); expanded = false }
                    )

                    true -> DropdownMenuItem(
                        text = { Text(text = "Disconnect") },
                        onClick = { disconnectDevice(); expanded = false }
                    )
                }
            }
        },
        backgroundColor = WhoppahTheme.colors.surface,
        contentColor = contentColorFor(WhoppahTheme.colors.surface),
        contentPadding = WindowInsets.statusBars.asPaddingValues(),
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
        backgroundColor = WhoppahTheme.colors.surface,
        contentColor = contentColorFor(WhoppahTheme.colors.surface),
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
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
        is HomeNavigationItem.ImageVectorIcon -> rememberVectorPainter(item.iconImageVector)
    }
    val selectedPainter = when (item) {
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
    val labelResId: StringResource,
    val contentDescriptionResId: StringResource,
) {
    class ImageVectorIcon(
        screen: HomeNavScreen,
        labelResId: StringResource,
        contentDescriptionResId: StringResource,
        val iconImageVector: ImageVector,
        val selectedImageVector: ImageVector? = null,
    ) : HomeNavigationItem(screen, labelResId, contentDescriptionResId)
}

private val HomeNavigationItems = listOf(
    HomeNavigationItem.ImageVectorIcon(
        screen = HomeNavScreen.Stats,
        labelResId = Res.string.home_tab_stats,
        contentDescriptionResId = Res.string.home_tab_stats,
        iconImageVector = Icons.Outlined.Analytics,
        selectedImageVector = Icons.Filled.Analytics,
    ),
    HomeNavigationItem.ImageVectorIcon(
        screen = HomeNavScreen.Prop,
        labelResId = Res.string.home_tab_prop,
        contentDescriptionResId = Res.string.home_tab_prop,
        iconImageVector = Icons.Outlined.SettingsApplications,
        selectedImageVector = Icons.Filled.SettingsApplications,
    ),
    HomeNavigationItem.ImageVectorIcon(
        screen = HomeNavScreen.Report,
        labelResId = Res.string.home_tab_report,
        contentDescriptionResId = Res.string.home_tab_report,
        iconImageVector = Icons.Outlined.Summarize,
        selectedImageVector = Icons.Filled.Summarize,
    ),
)


@Preview
@Composable
private fun Preview() {
    WhoppahTheme {
        HomeScreen(
            state = HomeViewState.Empty,
            actioner = {},
        )
    }
}