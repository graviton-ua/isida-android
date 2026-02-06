package ua.graviton.isida.ui.home.prop

import com.whoppah.common.resources.ComposableString

internal class PropListBuilder {
    private val list = mutableListOf<PropItem>()

    fun item(
        id: String,
        title: ComposableString,
        value: ComposableString,
    ) {
        list.add(
            PropItem.Default(
                id = id,
                title = title,
                value = value,
            )
        )
    }

    fun build() = list.toList()
}

internal fun buildProps(block: PropListBuilder.() -> Unit): List<PropItem> {
    val builder = PropListBuilder()
    builder.block()
    return builder.build()
}
