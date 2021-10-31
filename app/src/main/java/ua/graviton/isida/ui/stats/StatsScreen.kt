package ua.graviton.isida.ui.stats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ua.graviton.isida.ui.theme.IsidaTheme
import ua.graviton.isida.ui.utils.collectAsStateWithLifecycle

@Composable
fun StatsScreen() {
    //LaunchedEffect("once") { SystemBarColorManager.darkIcons.value = true }

    StatsScreen(
        viewModel = hiltViewModel(),
    )
}

@Composable
private fun StatsScreen(
    viewModel: StatsViewModel,
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    StatsScreen(viewState) { action ->
        when (action) {
            //is ShopCartAction.Close -> navigateUp()
            //is ShopCartAction.NavigateCheckout -> openCheckout()
            else -> viewModel.submitAction(action)
        }
    }
}

@Composable
private fun StatsScreen(
    state: StatsViewState,
    actioner: (StatsAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    IsidaTheme {
        StatsScreen(
            state = StatsViewState.Empty,
            actioner = {}
        )
    }
}