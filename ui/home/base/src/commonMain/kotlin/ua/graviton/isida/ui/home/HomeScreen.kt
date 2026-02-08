package ua.graviton.isida.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
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
import com.whoppah.common.resources.butPower
import com.whoppah.common.resources.disconnect
import com.whoppah.common.resources.label_connect
import com.whoppah.metrox.viewmodel.injectedViewModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.jetbrains.compose.resources.stringResource
import ua.graviton.isida.data.protocol.packets.TableDay
import ua.graviton.isida.ui.home.program.ProgramScreen
import ua.graviton.isida.ui.home.program.addProgramScreen
import ua.graviton.isida.ui.home.prop.PropScreen
import ua.graviton.isida.ui.home.prop.addPropScreen
import ua.graviton.isida.ui.home.stats.StatsScreen
import ua.graviton.isida.ui.home.stats.addStatsScreen
import ua.graviton.isida.ui.navigation.*
import ua.graviton.isida.ui.navigation.result.ResultEventBus

@Serializable
data object HomeScreen : NavKey

private val TOP_LEVEL_ROUTES: List<HomeTabScreen> = listOf(
    StatsScreen, PropScreen, ProgramScreen,
)

@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = injectedViewModel(),
    resultBus: ResultEventBus,
    connectDevice: () -> Unit,
    openPowerDialog: () -> Unit,
    openSetPropDialog: (String) -> Unit,
    navigateSetDay: (Int, TableDay) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        resultBus = resultBus,
        connectDevice = connectDevice,
        disconnectDevice = viewModel::disconnect,
        openPowerDialog = openPowerDialog,
        openSetPropDialog = openSetPropDialog,
        navigateSetDay = navigateSetDay,
    )
}

@Composable
private fun HomeScreen(
    state: HomeViewState,
    resultBus: ResultEventBus,
    connectDevice: () -> Unit,
    disconnectDevice: () -> Unit,
    openPowerDialog: () -> Unit,
    openSetPropDialog: (String) -> Unit,
    navigateSetDay: (Int, TableDay) -> Unit,
) {
    val navigationState = rememberNavigationState(
        configuration = config,
        startRoute = StatsScreen,
        topLevelRoutes = TOP_LEVEL_ROUTES.toSet(),
    )
    val navigator = remember(navigationState) { NavigatorImpl(navigationState) }
    val entryProvider = remember(navigator, resultBus) {
        entryProvider {
            addStatsScreen(navigator = navigator)
            addPropScreen(navigator = navigator, openSetPropDialog = openSetPropDialog)
            addProgramScreen(navigator = navigator, resultBus = resultBus, navigateSetDay = navigateSetDay)
        }
    }

    WhScaffold(
        topBar = {
            HomeTopBar(
                deviceConnected = state.deviceConnected,
                modifier = Modifier.fillMaxWidth(),
                connectDevice = connectDevice,
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
            if (deviceConnected) {
                IconButton(onClick = openPowerDialog) {
                    Icon(imageVector = Icons.Default.Tune, contentDescription = stringResource(Res.string.butPower))
                }
                TextButton(onClick = disconnectDevice) {
                    Text(text = stringResource(Res.string.disconnect))
                }
            } else {
                TextButton(onClick = connectDevice) {
                    Text(text = stringResource(Res.string.label_connect))
                }
            }
        },
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
            subclass(ProgramScreen::class, ProgramScreen.serializer())
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
            resultBus = ResultEventBus(),
            connectDevice = {},
            disconnectDevice = {},
            openPowerDialog = {},
            openSetPropDialog = {},
            navigateSetDay = { _, _ -> },
        )
    }
}