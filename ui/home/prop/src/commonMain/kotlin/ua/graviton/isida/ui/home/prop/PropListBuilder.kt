package ua.graviton.isida.ui.home.prop

import androidx.compose.ui.graphics.Color
import com.whoppah.common.resources.ComposableString

internal class PropListBuilder {
    private val list = mutableListOf<PropItem>()

    fun item(
        id: String,
        title: ComposableString,
        value: ComposableString,
        style: StyleBuilder.() -> Unit = {},
    ) {
        val builder = StyleBuilder()
        builder.style()
        list.add(
            PropItem.Default(
                id = id,
                title = title,
                value = value,
                style = builder.build(),
            )
        )
    }

    fun build() = list.toList()
}

internal class StyleBuilder {
    var backgroundColor: Color? = null
    var titleColor: Color? = null
    var titleBackgroundColor: Color? = null
    var valueColor: Color? = null
    var valueBackgroundColor: Color? = null

    fun build() = PropItem.Style(
        backgroundColor = backgroundColor,
        titleColor = titleColor,
        titleBackgroundColor = titleBackgroundColor,
        valueColor = valueColor,
        valueBackgroundColor = valueBackgroundColor,
    )
}

internal fun buildProps(block: PropListBuilder.() -> Unit): List<PropItem> {
    val builder = PropListBuilder()
    builder.block()
    return builder.build()
}
