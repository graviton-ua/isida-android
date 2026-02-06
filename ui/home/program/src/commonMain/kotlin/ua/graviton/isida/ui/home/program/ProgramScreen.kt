package ua.graviton.isida.ui.home.program

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Summarize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        onFetch = viewModel::fetchTable,
    )
}

@Composable
private fun ProgramScreen(
    state: ProgramViewState,
    onFetch: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ControlPanel(
            isLoading = state.isLoading,
            onFetch = onFetch,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        if (state.items.isEmpty() && !state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No data. Press Fetch to get program table.")
            }
        } else {
            ProgramTable(
                items = state.items,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun ControlPanel(
    isLoading: Boolean,
    onFetch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onFetch,
            enabled = !isLoading,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(text = "Fetch Table")
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
        }
    }
}

@Composable
private fun ProgramTable(
    items: List<ProgramViewState.ProgramItem>,
    modifier: Modifier = Modifier,
) {
    val horizontalScrollState = rememberScrollState()

    LazyColumn(modifier = modifier) {
        stickyHeader {
            HeaderRow(horizontalScrollState)
        }
        items(items) { item ->
            DataRow(item, horizontalScrollState)
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 0.5.dp,
                color = Color.LightGray
            )
        }
    }
}

@Composable
private fun HeaderRow(scrollState: ScrollState) {
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeaderCell("Day", Modifier.width(60.dp))
        HeaderCell("T0", Modifier.width(80.dp))
        HeaderCell("T1", Modifier.width(80.dp))
        HeaderCell("Rh", Modifier.width(60.dp))
        HeaderCell("Flp", Modifier.width(60.dp))
        HeaderCell("Tr", Modifier.width(60.dp))
        HeaderCell("Cl", Modifier.width(60.dp))
    }
}

@Composable
private fun RowScope.HeaderCell(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelMedium
    )
}

@Composable
private fun DataRow(item: ProgramViewState.ProgramItem, scrollState: ScrollState) {
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(vertical = 12.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DataCell(item.day.toString(), Modifier.width(60.dp))
        DataCell(item.t0.toString(), Modifier.width(80.dp))
        DataCell(item.t1.toString(), Modifier.width(80.dp))
        DataCell(item.rh.toString(), Modifier.width(60.dp))
        DataCell(item.flp.toString(), Modifier.width(60.dp))
        DataCell(item.tr.toString(), Modifier.width(60.dp))
        DataCell(item.cl.toString(), Modifier.width(60.dp))
    }
}

@Composable
private fun RowScope.DataCell(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium
    )
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    WhoppahTheme {
        ProgramScreen(
            state = ProgramViewState(
                items = listOf(
                    ProgramViewState.ProgramItem(1, 37.8f, 28.5f, 55, 0, 1, 0),
                    ProgramViewState.ProgramItem(2, 37.7f, 28.4f, 55, 0, 1, 0),
                )
            ),
            onFetch = {}
        )
    }
}