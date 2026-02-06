package ua.graviton.isida.ui.home.prop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SettingsApplications
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whoppah.common.compose.backgroundNotNull
import com.whoppah.common.compose.icons.WhIcons
import com.whoppah.common.compose.icons.action.Edit
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.resources.Res
import com.whoppah.common.resources.home_tab_prop
import com.whoppah.metrox.viewmodel.injectedViewModel
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import ua.graviton.isida.ui.navigation.HomeTabScreen

@Serializable
data object PropScreen : HomeTabScreen {
    override val icon: ImageVector = Icons.Outlined.SettingsApplications
    override val title: StringResource = Res.string.home_tab_prop
}

@Composable
internal fun PropScreen(
    viewModel: PropViewModel = injectedViewModel(),
    openSetPropDialog: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PropScreen(
        state = state,
        navigateSetPropDialog = openSetPropDialog,
    )
}

@Composable
private fun PropScreen(
    state: PropViewState,
    navigateSetPropDialog: (String) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(vertical = 8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = state.items,
            key = { _, it -> it.id },
        ) { index, item ->
            Item(
                item = item,
                onClick = { navigateSetPropDialog(item.id) },
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
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .clickable(onClick = onClick)
            .backgroundNotNull(color = item.style.backgroundColor)
            .defaultMinSize(minHeight = 42.dp)
            .padding(horizontal = 12.dp),
    ) {
        Text(
            text = item.title.text(),
            color = item.style.titleColor ?: LocalContentColor.current,
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .backgroundNotNull(color = item.style.titleBackgroundColor),
        )
        Text(
            text = item.value.text(),
            color = item.style.valueColor ?: LocalContentColor.current,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .backgroundNotNull(color = item.style.valueBackgroundColor),
        )
        Icon(
            imageVector = WhIcons.Action.Edit,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    WhoppahTheme {
        PropScreen(
            state = PropViewState.Preview,
            navigateSetPropDialog = {}
        )
    }
}