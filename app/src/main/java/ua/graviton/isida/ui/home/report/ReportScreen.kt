package ua.graviton.isida.ui.home.report

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import timber.log.Timber
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
    //Text(text = "Report screen")

    NamePicker(header = state.header, names = state.items, onNameClicked = { actioner(ReportAction.Start) })
}

/**
 * Display a list of names the user can click with a header
 */
@Composable
fun NamePicker(
    header: String,
    names: List<String>,
    onNameClicked: (String) -> Unit
) {
    Column {
        Timber.d("NamePicker before header")
        // this will recompose when [header] changes, but not when [names] changes
        Text(text = header, style = MaterialTheme.typography.h5)
        Divider()

        Timber.d("NamePicker after header")
        // LazyColumn is the Compose version of a RecyclerView.
        // The lambda passed to items() is similar to a RecyclerView.ViewHolder.
        LazyColumn {
            items(names) { name ->
                // When an item's [name] updates, the adapter for that item
                // will recompose. This will not recompose when [header] changes
                NamePickerItem(name, onNameClicked)
            }
        }

        Button(onClick = { onNameClicked("sad") }) {
            Text(text = "start")
        }
    }
}

/**
 * Display a single name the user can click.
 */
@Composable
private fun NamePickerItem(name: String, onClicked: (String) -> Unit) {
    Text(name, Modifier.clickable(onClick = { onClicked(name) }))
    Timber.d("NamePickerItem name: $name")
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