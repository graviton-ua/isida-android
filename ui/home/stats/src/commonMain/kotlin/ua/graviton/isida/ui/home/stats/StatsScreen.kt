package ua.graviton.isida.ui.home.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.resources.CellNum
import com.whoppah.common.resources.Res
import com.whoppah.common.resources.home_tab_stats
import com.whoppah.metrox.viewmodel.injectedViewModel
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ua.graviton.isida.ui.navigation.HomeTabScreen

@Serializable
data object StatsScreen : HomeTabScreen {
    override val icon: ImageVector = Icons.Outlined.Analytics
    override val title: StringResource = Res.string.home_tab_stats
}

@Composable
internal fun StatsScreen(
    viewModel: StatsViewModel = injectedViewModel(),
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    StatsScreen(
        state = viewState,
    )
}

@Composable
private fun StatsScreen(
    state: StatsViewState,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        contentPadding = WindowInsets.statusBars.add(WindowInsets(left = 12.dp, right = 12.dp)).asPaddingValues(),
        modifier = Modifier.fillMaxSize()
    ) {
        stickyHeader {
            Text(
                text = stringResource(Res.string.CellNum, state.titleDeviceId?.toString() ?: "--"),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(state.titleDeviceBackgroundColor ?: Color.Transparent)
            )
        }

        items(state.items, { it.titleResId.key }) {
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
            text = stringResource(item.titleResId),
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

@Preview(showBackground = true)
@Composable
private fun Preview() {
    WhoppahTheme {
        StatsScreen(
            state = StatsViewState.Preview,
        )
    }
}