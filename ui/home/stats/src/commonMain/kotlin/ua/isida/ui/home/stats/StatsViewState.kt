package ua.isida.ui.home.stats

import androidx.compose.ui.graphics.Color

/**
 * Представляет собой неизменяемое состояние пользовательского интерфейса для экрана статистики.
 * Это состояние создается [StatsViewModel] и потребляется [StatsScreen].
 *
 * @property titleDeviceId Уникальный идентификатор устройства (например, ID узла) для отображения в заголовке.
 * @property titleDeviceBackgroundColor Фоновый цвет для заголовка с названием устройства, обычно используемый для индикации статуса подключения или работы устройства (например, зеленый для OK, красный для ошибки).
 * @property items Список строк [StatsItem] (заголовки и информационные элементы) для отображения в списке.
 */
internal data class StatsViewState(
    val deviceConnected: Boolean,
    val titleDeviceId: Int?,
    val titleDeviceBackgroundColor: Color?,

    val items: List<StatsItem>,
) {
    companion object {
        /**
         * Начальное пустое состояние, используемое до загрузки каких-либо данных.
         * Содержит элементы-заполнители для определения структуры списка.
         */
        val Empty = StatsViewState(
            deviceConnected = false,
            titleDeviceId = null,
            titleDeviceBackgroundColor = null,
            items = emptyList(),
        )

        /**
         * Состояние предварительного просмотра, заполненное фиктивными данными, подходящее для предварительного просмотра в инструментах UI.
         */
        val Preview = StatsViewState(
            deviceConnected = true,
            titleDeviceId = 1,
            titleDeviceBackgroundColor = null,
            items = emptyList(),
        )
    }
}