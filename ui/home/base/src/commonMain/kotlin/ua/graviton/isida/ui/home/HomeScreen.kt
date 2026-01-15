package ua.graviton.isida.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.compose.ui.WhScaffold
import com.whoppah.common.compose.ui.WhTopAppBar
import com.whoppah.common.resources.Res
import com.whoppah.common.resources.app_name
import com.whoppah.metrox.viewmodel.injectedViewModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.jetbrains.compose.resources.stringResource
import ua.graviton.isida.ui.home.prop.PropScreen
import ua.graviton.isida.ui.home.prop.addPropScreen
import ua.graviton.isida.ui.home.report.ReportScreen
import ua.graviton.isida.ui.home.report.addReportScreen
import ua.graviton.isida.ui.home.stats.StatsScreen
import ua.graviton.isida.ui.home.stats.addStatsScreen
import ua.graviton.isida.ui.navigation.*

@Serializable
data object HomeScreen : NavKey

// @Composable
// fun HomeScreen(
//     openPowerDialog: () -> Unit,
//     openSetPropDialog: (String) -> Unit,
// ) {
//     LaunchedEffect(Unit) { SystemBarColorManager.darkIcons.value = true }
//
//     val context = LocalContext.current
//
//     val scanForDevice = rememberLauncherForActivityResult(ScanForDeviceResultContract()) { address ->
//         Timber.d("Selected device: $address | start service")
//         if (address != null) with(context) { startService(intentBLServiceConnectDevice(address)) }
//     }
//
//     HomeScreen(
//         viewModel = hiltViewModel(),
//         connectDevice = { scanForDevice.launch(Unit) },
//         disconnectDevice = { with(context) { startService(intentBLServiceDisconnectDevice()) } },
//         openPowerDialog = openPowerDialog,
//         openSetPropDialog = openSetPropDialog,
//     )
// }

private val TOP_LEVEL_ROUTES: List<HomeTabScreen> = listOf(
    StatsScreen, PropScreen, ReportScreen,
)

@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = injectedViewModel(),
    connectDevice: () -> Unit,
    openPowerDialog: () -> Unit,
    openSetPropDialog: (String) -> Unit,
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = viewState,
        disconnectDevice = viewModel::disconnect,
        openPowerDialog = openPowerDialog,
        openSetPropDialog = openSetPropDialog,
    ) { action ->
        when (action) {
            //is ShopCartAction.Close -> navigateUp()
            is HomeAction.ConnectDevice -> connectDevice()
            else -> viewModel.submitAction(action)
        }
    }
}

@Composable
private fun HomeScreen(
    state: HomeViewState,
    disconnectDevice: () -> Unit,
    openPowerDialog: () -> Unit,
    openSetPropDialog: (String) -> Unit,
    actioner: (HomeAction) -> Unit,
) {
    val navigationState = rememberNavigationState(
        configuration = config,
        startRoute = StatsScreen,
        topLevelRoutes = TOP_LEVEL_ROUTES.toSet(),
    )
    val navigator = remember(navigationState) { NavigatorImpl(navigationState) }
    val entryProvider = remember(navigator) {
        entryProvider {
            addStatsScreen(navigator = navigator)
            addPropScreen(navigator = navigator, openSetPropDialog = openSetPropDialog)
            addReportScreen(navigator = navigator)
        }
    }

    WhScaffold(
        topBar = {
            HomeTopBar(
                deviceConnected = state.deviceConnected,
                modifier = Modifier.fillMaxWidth(),
                connectDevice = { actioner(HomeAction.ConnectDevice) },
                disconnectDevice = disconnectDevice,
                openPowerDialog = openPowerDialog,
            )
        },
        bottomBar = {
            MainBottomBar(
                modifier = Modifier.fillMaxWidth(),
            ) {
                TOP_LEVEL_ROUTES.forEach {
                    TabItem(
                        screen = it,
                        selected = it == navigationState.topLevelRoute,
                        onSelect = { navigator.navigateTo(it) },
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                    )
                }
            }
        }
    ) { paddings ->
        val dialogStrategy = remember { DialogSceneStrategy<NavKey>() }
        NavDisplay(
            entries = navigationState.toEntries(entryProvider),
            onBack = navigator::navigateUp,
            sceneStrategy = dialogStrategy,
            modifier = Modifier.padding(paddings),
        )
    }
}


@Composable
private fun HomeTopBar(
    deviceConnected: Boolean,
    modifier: Modifier = Modifier,
    connectDevice: () -> Unit,
    disconnectDevice: () -> Unit,
    openPowerDialog: () -> Unit,
) {
    WhTopAppBar(
        title = { Text(text = stringResource(Res.string.app_name)) },
        actions = {
            var expanded by remember { mutableStateOf(false) }
            if (deviceConnected)
                TextButton(onClick = openPowerDialog) {
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
        //backgroundColor = WhoppahTheme.colors.surface,
        //contentColor = contentColorFor(WhoppahTheme.colors.surface),
        //contentPadding = WindowInsets.statusBars.asPaddingValues(),
        modifier = modifier
    )
}


@Composable
private fun TabItem(
    screen: HomeTabScreen,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Item(
        isSelected = selected,
        onClick = onSelect,
        icon = screen.icon,
        title = stringResource(screen.title),
        modifier = modifier,
    )
}

private class NavigatorImpl(val state: NavigationState) : Navigator {

    override fun navigateUp() {
        val currentStack = state.backStacks[state.topLevelRoute] ?: error("Stack for ${state.topLevelRoute} not found")
        val currentRoute = currentStack.last()

        // If we're at the base of the current route, go back to the start route stack.
        if (currentRoute == state.topLevelRoute) {
            state.topLevelRoute = state.startRoute
        } else {
            currentStack.removeLastOrNull()
        }
    }

    override fun navigateTo(key: NavKey) {
        if (key in state.backStacks.keys) {
            // This is a top level route, just switch to it
            state.topLevelRoute = key
        } else {
            state.backStacks[state.topLevelRoute]?.add(key)
        }
    }
}

private val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(PropScreen::class, PropScreen.serializer())
            subclass(ReportScreen::class, ReportScreen.serializer())
            subclass(StatsScreen::class, StatsScreen.serializer())
        }
    }
}


@Preview
@Composable
private fun Preview() {
    WhoppahTheme {
        HomeScreen(
            state = HomeViewState.Empty,
            disconnectDevice = {},
            openPowerDialog = {},
            openSetPropDialog = {},
            actioner = {},
        )
    }
}