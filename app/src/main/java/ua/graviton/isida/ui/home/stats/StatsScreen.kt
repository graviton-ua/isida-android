package ua.graviton.isida.ui.home.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import ua.graviton.isida.R
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
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyTop = false,
            applyBottom = false,
            additionalStart = 12.dp,
            additionalEnd = 12.dp,
        ),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = stringResource(id = R.string.CellNum, state.titleDeviceId?.toString() ?: "--"),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(state.titleDeviceBackgroundColor ?: Color.Transparent)
            )
        }

        items(state.items, { it.titleResId }) {
            Item(item = it)
        }
    }
}

@Composable
private fun Item(
    item: StatsItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = item.backgroundColor ?: Color.Transparent)
    ) {
        Text(
            text = stringResource(id = item.titleResId),
            color = item.valueColor ?: Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        val value = when (val v = item.value) {
            is StatsItem.Value.FloatVal -> {
                val value = v.value?.let { String.format("%.1f", it) }
                if (v.target == null) value ?: "--" else value?.let { it + "  [${v.target}]" } ?: "--"
            }
            is StatsItem.Value.IntVal -> {
                val value = v.value?.toString()
                if (v.target == null) value ?: "--" else value?.let { it + "  [${v.target}]" } ?: "--"
            }
            is StatsItem.Value.TextRaw -> {
                if (v.target == null) v.value ?: "--" else v.value?.let { it + "  [${v.target}]" } ?: "--"
            }
            is StatsItem.Value.TextResId -> {
                if (v.target == null)
                    v.value?.let { stringResource(it) } ?: "--"
                else
                    v.value?.let { stringResource(it) + "  [${stringResource(v.target)}]" } ?: "--"

            }
            null -> "--"
        }
        Text(
            text = value,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = android.graphics.Color.WHITE.toLong()
)
@Composable
private fun Preview() {
    IsidaTheme {
        StatsScreen(
            state = StatsViewState.Preview,
            actioner = {}
        )
    }
}