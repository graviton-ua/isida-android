package ua.graviton.isida.ui.prop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.systemBarsPadding
import ua.graviton.isida.ui.theme.IsidaTheme
import ua.graviton.isida.ui.utils.collectAsStateWithLifecycle

@Composable
fun PropScreen() {
    //LaunchedEffect("once") { SystemBarColorManager.darkIcons.value = true }

    PropScreen(
        viewModel = hiltViewModel(),
    )
}

@Composable
private fun PropScreen(
    viewModel: PropViewModel,
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    PropScreen(viewState) { action ->
        when (action) {
            //is ShopCartAction.Close -> navigateUp()
            //is ShopCartAction.NavigateCheckout -> openCheckout()
            else -> viewModel.submitAction(action)
        }
    }
}

@Composable
private fun PropScreen(
    state: PropViewState,
    actioner: (PropAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(top = true)
    ) {
        Text(text = "Prop screen")
    }
}

@Preview
@Composable
private fun Preview() {
    IsidaTheme {
        PropScreen(
            state = PropViewState.Empty,
            actioner = {}
        )
    }
}