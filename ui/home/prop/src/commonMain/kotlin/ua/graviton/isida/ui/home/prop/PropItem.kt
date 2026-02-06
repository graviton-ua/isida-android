package ua.graviton.isida.ui.home.prop

import androidx.compose.runtime.Immutable
import com.whoppah.common.resources.ComposableString

@Immutable
sealed interface PropItem {
    val id: String
    val title: ComposableString
    val value: ComposableString


    @Immutable
    data class Default(
        override val id: String,
        override val title: ComposableString,
        override val value: ComposableString,
    ) : PropItem
}