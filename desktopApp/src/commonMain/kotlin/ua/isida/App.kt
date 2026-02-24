package ua.isida

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import ua.isida.common.ui.resources.Res
import ua.isida.common.ui.resources.icon
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import ua.isida.shared.ui.IsidaApp

@Composable
fun ApplicationScope.App(state: AppState) {
    ApplicationTray(state)

    Window(
        state = rememberWindowState(width = 450.dp, height = 800.dp),
        title = "Svg2Compose",
        icon = painterResource(Res.drawable.icon),
        onCloseRequest = state::exit,
    ) {
        IsidaApp()
    }
}

@Composable
private fun ApplicationScope.ApplicationTray(state: AppState) {
    Tray(
        icon = painterResource(Res.drawable.icon),
        state = state.tray,
        tooltip = "Notepad",
        menu = { ApplicationMenu(state) }
    )
}

@Composable
private fun MenuScope.ApplicationMenu(state: AppState) {
    val scope = rememberCoroutineScope()
    fun exit() = scope.launch { state.exit() }

    //Item("New", onClick = state::newWindow)
    //Separator()
    Item("Exit", onClick = { exit() })
}