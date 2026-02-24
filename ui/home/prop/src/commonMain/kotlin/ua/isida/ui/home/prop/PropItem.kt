package ua.isida.ui.home.prop

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import ua.isida.common.ui.resources.ComposableString

@Immutable
sealed interface PropItem {
    val id: String
    val title: ComposableString
    val value: ComposableString
    val style: Style


    @Immutable
    data class Style(
        val backgroundColor: Color? = null,
        val titleColor: Color? = null,
        val titleBackgroundColor: Color? = null,
        val valueColor: Color? = null,
        val valueBackgroundColor: Color? = null,
    )


    @Immutable
    data class Default(
        override val id: String,
        override val title: ComposableString,
        override val value: ComposableString,
        override val style: Style = Style(),
    ) : PropItem
}