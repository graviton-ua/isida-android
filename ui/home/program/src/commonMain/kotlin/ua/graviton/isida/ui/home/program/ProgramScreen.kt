package ua.graviton.isida.ui.home.program

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Summarize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.resources.Res
import com.whoppah.common.resources.home_tab_programtable
import com.whoppah.metrox.viewmodel.injectedViewModel
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import ua.graviton.isida.ui.navigation.HomeTabScreen

@Serializable
data object ProgramScreen : HomeTabScreen {
    override val icon: ImageVector = Icons.Outlined.Summarize
    override val title: StringResource = Res.string.home_tab_programtable
}

@Composable
internal fun ProgramScreen(
    viewModel: ProgramViewModel = injectedViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProgramScreen(
        state = state,
    )
}

@Composable
private fun ProgramScreen(
    state: ProgramViewState,
) {

}


@Preview
@Composable
private fun Preview() {
    WhoppahTheme {
        ProgramScreen(
            state = ProgramViewState.Empty,
        )
    }
}