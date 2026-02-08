package ua.graviton.isida.ui.home.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whoppah.common.compose.backgroundNotNull
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.compose.ui.DeviceNotConnectedPlaceholder
import com.whoppah.common.resources.Res
import com.whoppah.common.resources.home_tab_stats
import com.whoppah.metrox.viewmodel.injectedViewModel
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import ua.graviton.isida.ui.navigation.HomeTabScreen

/**
 * Определяет маршрут экрана статистики и метаданные для навигации.
 */
@Serializable
data object StatsScreen : HomeTabScreen {
    override val icon: ImageVector = Icons.Outlined.Analytics
    override val title: StringResource = Res.string.home_tab_stats
}

/**
 * Основная точка входа экрана для отображения статистики устройства.
 * Подключает состояние [StatsViewModel] к пользовательскому интерфейсу с помощью [collectAsStateWithLifecycle].
 *
 * @param viewModel Экземпляр ViewModel, внедряемый по умолчанию.
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
 * Stateless-реализация экрана статистики.
 * Отрисовывает закрепленный заголовок с ID устройства и прокручиваемый список элементов [StatsItem].
 *
 * @param state Текущее состояние пользовательского интерфейса для отрисовки.
 */
@Composable
private fun StatsScreen(
    state: StatsViewState,
) {
    if (!state.deviceConnected) {
        DeviceNotConnectedPlaceholder()
    } else {
        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState,
            contentPadding = WindowInsets.statusBars.add(WindowInsets(left = 12.dp, right = 12.dp)).asPaddingValues(),
            modifier = Modifier.fillMaxSize()
        ) {
            state.items.forEach { item ->
                when (item) {
                    is StatsItem.Header -> stickyHeader { HeaderItem(item) }
                    is StatsItem.Info -> item { InfoItem(item) }
                }
            }
        }
    }
}

/**
 * Отрисовывает элемент закрепленного заголовка.
 *
 * @param item Элемент данных заголовка.
 */
@Composable
private fun HeaderItem(item: StatsItem.Header) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundNotNull(color = item.style.backgroundColor ?: Color.LightGray) // Цвет заголовка по умолчанию, если не указан
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Text(
            text = item.title.text(),
            fontWeight = FontWeight.Bold,
            color = item.style.titleColor ?: LocalContentColor.current
        )
    }
}

@Composable
private fun InfoItem(
    item: StatsItem.Info,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = item.title.text(),
            color = item.style.titleColor ?: LocalContentColor.current,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Text(
            text = item.content.text(),
            color = item.style.valueColor ?: LocalContentColor.current,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .backgroundNotNull(color = item.style.backgroundColor)
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
