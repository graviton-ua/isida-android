package ua.graviton.isida.ui.home.prop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ua.graviton.isida.ui.theme.IsidaTheme
import ua.graviton.isida.ui.utils.rememberFlowWithLifecycle

@Composable
fun PropScreen(
    openSetPropDialog: (String) -> Unit,
) {
    PropScreen(
        viewModel = hiltViewModel(),
        openSetPropDialog = openSetPropDialog,
    )
}

@Composable
private fun PropScreen(
    viewModel: PropViewModel,
    openSetPropDialog: (String) -> Unit,
) {
    val viewState by rememberFlowWithLifecycle(viewModel.state).collectAsState(initial = PropViewState.Init)

    PropScreen(viewState) { action ->
        when (action) {
            is PropAction.SetPropDialog -> openSetPropDialog(action.id)
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
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(vertical = 8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = state.items,
            key = { it.id },
        ) { item ->
            Item(
                item = item,
                click = { actioner(it) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun Item(
    item: PropItem,
    click: (PropAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(horizontal = 12.dp)
    ) {
        Text(
            text = item.title.asString(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Text(
                text = item.value.asString(),
            )
            IconButton(onClick = { click(PropAction.SetPropDialog(item.id)) }) { Icon(Icons.Default.Edit, "edit") }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = android.graphics.Color.LTGRAY.toLong()
)
@Composable
private fun Preview() {
    IsidaTheme {
        PropScreen(
            state = PropViewState.Preview,
            actioner = {}
        )
    }
}