package ua.graviton.isida.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.*
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.whoppah.common.compose.theme.WhoppahTheme
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ua.graviton.isida.ui.devicemode.DeviceModeDialog
import ua.graviton.isida.ui.devicemode.addDeviceModeDialog
import ua.graviton.isida.ui.home.HomeScreen
import ua.graviton.isida.ui.home.addHomeScreen
import ua.graviton.isida.ui.navigation.NavigatorWithResultBus
import ua.graviton.isida.ui.navigation.result.ResultEventBus
import ua.graviton.isida.ui.scan.ScanDevicesScreen
import ua.graviton.isida.ui.scan.addScanDevicesScreen
import ua.graviton.isida.ui.setday.addSetDayScreen
import ua.graviton.isida.ui.setprop.SetPropDialog
import ua.graviton.isida.ui.setprop.addSetPropDialog

@Composable
fun IsidaApp(
    modifier: Modifier = Modifier,
) {
    WhoppahTheme {
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
                    openPowerDialog = { navigator.navigateTo(DeviceModeDialog) },
                    openSetPropDialog = { navigator.navigateTo(SetPropDialog(it)) },
                )
                addDeviceModeDialog(navigator = navigator)
                addScanDevicesScreen(navigator = navigator)
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
        }
    }
}