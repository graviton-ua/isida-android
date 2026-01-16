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

/**
 * The main screen for displaying device statistics.
 * Connects the [StatsViewModel] state to the UI.
 */
@Composable
internal fun StatsScreen(
    viewModel: StatsViewModel = injectedViewModel(),
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    StatsScreen(
        state = viewState,
    )
}

/**
 * Stateless implementation of the Stats Screen.
 * Renders a sticky header with the device ID and a scrollable list of [StatsItem]s.
 */
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

/**
 * Renders a single [StatsItem].
 * Handles different content types (Numeric, TextResource, TextRaw) and formatting.
 */
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
            color = (item.content as? StatsItem.Content.Numeric<*>)?.valueColor
                ?: (item.content as? StatsItem.Content.TextResource)?.valueColor
                ?: (item.content as? StatsItem.Content.TextRaw)?.valueColor
                ?: Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        val valueText = when (val content = item.content) {
            is StatsItem.Content.Numeric<*> -> {
                val v = content.value
                val t = content.target
                if (v == null) "--"
                else {
                    val vStr = if (v is Float) String.format("%.1f", v) else v.toString()
                    if (t != null) {
                        val tStr = if (t is Float) String.format("%.1f", t) else t.toString()
                        "$vStr  [$tStr]"
                    } else {
                        vStr
                    }
                }
            }

            is StatsItem.Content.TextResource -> {
                val v = content.value?.let { stringResource(it) }
                val t = content.target?.let { stringResource(it) }
                if (v == null) "--"
                else if (t != null) "$v  [$t]"
                else v
            }

            is StatsItem.Content.TextRaw -> {
                val v = content.value
                val t = content.target
                if (v == null) "--"
                else if (t != null) "$v  [$t]"
                else v
            }
        }

        Text(
            text = valueText,
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