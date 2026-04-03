package ua.isida.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.*
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import co.touchlab.kermit.Logger
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ua.isida.common.ui.compose.theme.AppTheme
import ua.isida.common.ui.navigation.NavigatorWithResultBus
import ua.isida.common.ui.navigation.result.ResultEventBus
import ua.isida.ui.devicemode.DeviceModeDialog
import ua.isida.ui.devicemode.addDeviceModeDialog
import ua.isida.ui.home.HomeScreen
import ua.isida.ui.home.addHomeScreen
import ua.isida.ui.logreport.LogReportScreen
import ua.isida.ui.logreport.addLogReportScreen
import ua.isida.ui.scan.ScanDevicesScreen
import ua.isida.ui.scan.addScanDevicesScreen
import ua.isida.ui.setday.SetDayScreen
import ua.isida.ui.setday.addSetDayScreen
import ua.isida.ui.setprop.SetPropDialog
import ua.isida.ui.setprop.addSetPropDialog

@Composable
fun IsidaApp(
    modifier: Modifier = Modifier,
) {
    AppTheme {
        LaunchedEffect(Unit) {
            Logger.i { "IsidaApp initialized. Starting log test sequence..." }
        }
        val backStack = rememberNavBackStack(configuration = config, HomeScreen)
        val navigator = remember(backStack) { NavigatorImpl(backStack) }
        val dialogStrategy = remember { DialogSceneStrategy<NavKey>() }

        NavDisplay(
            backStack = backStack,
            sceneStrategy = dialogStrategy,
            entryProvider = entryProvider {
                addHomeScreen(
                    navigator = navigator,
                    navigateScanDevices = { navigator.navigateTo(ScanDevicesScreen) },
                    onShowLogs = { navigator.navigateTo(LogReportScreen) },
                    openPowerDialog = { navigator.navigateTo(DeviceModeDialog) },
                    openSetPropDialog = { navigator.navigateTo(SetPropDialog(it)) },
                    navigateSetDay = { index, day -> navigator.navigateTo(SetDayScreen(index, day)) },
                )
                addDeviceModeDialog(navigator = navigator)
                addScanDevicesScreen(navigator = navigator)
                addLogReportScreen(navigator = navigator)
                addSetPropDialog(navigator = navigator)
                addSetDayScreen(navigator = navigator)
            },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            modifier = modifier,
        )
    }
}

private class NavigatorImpl(
    val backStack: NavBackStack<NavKey>,
) : NavigatorWithResultBus {

    override val resultBus: ResultEventBus = ResultEventBus()

    override fun navigateUp() {
        // If backstack contains only one destination, we shouldn't allow to go back
        if (backStack.size == 1) return

        backStack.removeLastOrNull()
    }

    override fun navigateTo(key: NavKey) {
        backStack.add(key)
    }
}

// Creates the required serializing configuration for open polymorphism
private val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(HomeScreen::class, HomeScreen.serializer())
            subclass(DeviceModeDialog::class, DeviceModeDialog.serializer())
            subclass(ScanDevicesScreen::class, ScanDevicesScreen.serializer())
            subclass(SetPropDialog::class, SetPropDialog.serializer())
            subclass(LogReportScreen::class, LogReportScreen.serializer())
        }
    }
}