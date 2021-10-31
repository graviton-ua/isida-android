package ua.graviton.isida.ui.home.report

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
fun ReportScreen() {
    //LaunchedEffect("once") { SystemBarColorManager.darkIcons.value = true }

    ReportScreen(
        viewModel = hiltViewModel(),
    )
}

@Composable
private fun ReportScreen(
    viewModel: ReportViewModel,
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    ReportScreen(viewState) { action ->
        when (action) {
            //is ShopCartAction.Close -> navigateUp()
            //is ShopCartAction.NavigateCheckout -> openCheckout()
            else -> viewModel.submitAction(action)
        }
    }
}

@Composable
private fun ReportScreen(
    state: ReportViewState,
    actioner: (ReportAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(top = true)
    ) {
        Text(text = "Report screen")
    }
}

@Preview
@Composable
private fun Preview() {
    IsidaTheme {
        ReportScreen(
            state = ReportViewState.Empty,
            actioner = {}
        )
    }
}