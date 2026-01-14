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
import ua.graviton.isida.ui.home.HomeScreen
import ua.graviton.isida.ui.home.addHomeScreen
import ua.graviton.isida.ui.navigation.Navigator

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
                addHomeScreen(navigator = navigator)
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
) : Navigator {
    override fun navigateUp() {
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
        }
    }
}