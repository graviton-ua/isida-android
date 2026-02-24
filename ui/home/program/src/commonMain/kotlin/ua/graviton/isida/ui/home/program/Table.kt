package ua.graviton.isida.ui.home.program

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import ua.isida.data.protocol.packets.TableDay
import ua.graviton.isida.ui.home.program.Table.Cell

@Immutable
data class Table(
    val rows: List<Row>,
) {
    @Immutable
    sealed interface Row {
        val cells: List<Cell>

        @Immutable
        data class StickyHeader(
            override val cells: List<Cell>,
        ) : Row

        @Immutable
        data class Header(
            override val cells: List<Cell>,
        ) : Row

        @Immutable
        data class Day(
            val index: Int,
            val day: TableDay,
            override val cells: List<Cell>,
        ) : Row
    }

    @Immutable
    data class Cell(
        val value: @Composable () -> String,
        val style: Style = Style(),
        val width: Dp = Dp.Unspecified,
    ) {
        @Immutable
        data class Style(
            val color: Color? = null,
            val backgroundColor: Color? = null,
        )
    }
}

internal class TableBuilder {
    private val rows = mutableListOf<Table.Row>()

    fun stickyHeader(block: RowBuilder.() -> Unit) {
        val builder = RowBuilder()
        builder.block()
        rows.add(Table.Row.StickyHeader(cells = builder.build()))
    }

    fun header(block: RowBuilder.() -> Unit) {
        val builder = RowBuilder()
        builder.block()
        rows.add(Table.Row.Header(cells = builder.build()))
    }

    fun day(index: Int, day: TableDay, block: RowBuilder.() -> Unit) {
        val builder = RowBuilder()
        builder.block()
        rows.add(Table.Row.Day(cells = builder.build(), index = index, day = day))
    }

    fun build(): Table = Table(rows = rows)
}

internal class RowBuilder {
    private val cells = mutableListOf<Cell>()

    fun cell(
        width: Dp = Dp.Unspecified,
        style: StyleBuilder.() -> Unit = {},
        text: @Composable () -> String,
    ) {
        val builder = StyleBuilder()
        builder.style()
        cells.add(Cell(value = text, style = builder.build(), width = width))
    }

    fun build() = cells.toList()
}

internal class StyleBuilder {
    var color: Color? = null
    var backgroundColor: Color? = null

    fun build() = Cell.Style(
        color = color,
        backgroundColor = backgroundColor,
    )
}

internal fun buildTable(block: TableBuilder.() -> Unit): Table {
    val builder = TableBuilder()
    builder.block()
    return builder.build()
}