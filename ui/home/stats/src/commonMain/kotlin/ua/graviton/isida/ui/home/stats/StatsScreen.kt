package ua.graviton.isida.ui.home.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
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
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.resources.CellNum
import com.whoppah.common.resources.Res
import com.whoppah.common.resources.home_tab_stats
import com.whoppah.metrox.viewmodel.injectedViewModel
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
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

        state.items.forEach { item ->
            when (item) {
                is StatsItem.Header -> stickyHeader(key = item.id) { HeaderItem(item) }
                is StatsItem.Info -> item(key = item.id) { InfoItem(item) }
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
            .background(item.backgroundColor ?: Color.LightGray) // Цвет заголовка по умолчанию, если не указан
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Text(
            text = item.title.asString(),
            fontWeight = FontWeight.Bold,
        )
    }
}

/**
 * Отрисовывает одну информационную строку [StatsItem.Info].
 * Обрабатывает различные типы контента (числовой, строковый ресурс, обычный текст) и форматирование.
 * Фоновый цвет применяется только к части строки со значением.
 *
 * @param item Элемент данных информации.
 */
@Composable
private fun InfoItem(
    item: StatsItem.Info
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = item.title.asString(),
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
                val v = content.values.map { stringResource(it) }.joinToString()
                val t = content.targets.map { stringResource(it) }.joinToString()
                if (v.isEmpty()) "--"
                else if (t.isNotEmpty()) "$v  [$t]"
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
                .background(color = item.backgroundColor ?: Color.Transparent)
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
