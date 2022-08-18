package ua.graviton.isida.ui.home.prop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ua.graviton.isida.ui.theme.IsidaTheme
import ua.graviton.isida.ui.utils.collectAsStateWithLifecycle

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
    val viewState by viewModel.state.collectAsStateWithLifecycle()

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
        itemsIndexed(
            items = state.items,
            key = { index, it -> it.id },
        ) { index, item ->
            Item(
                item = item,
                click = { actioner(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = if (index.mod(2) == 0) Color.Unspecified else Color.White.copy(alpha = 0.2f)),
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
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .clickable(onClick = { click(PropAction.SetPropDialog(item.id)) })
            .defaultMinSize(minHeight = 42.dp)
            .padding(horizontal = 12.dp)
    ) {
        Text(
            text = item.title.asString(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
        )
        Text(
            text = item.value.asString(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
        )
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