package ua.graviton.isida.ui.home.prop

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ua.graviton.isida.R

data class PropViewState(
    val items: List<PropItem>
) {
    companion object {
        val Init = PropViewState(emptyList())
        val Preview = PropViewState(
            items = listOf(
                PropItem(id = "1", title = PropItem.Title.Text("Some item example"), value = PropItem.Value.Data(23), action = null),
                PropItem(id = "2", title = PropItem.Title.Text("item example"), value = PropItem.Value.Data(23.2333), action = null),
                PropItem(id = "3", title = PropItem.Title.Text("Some item"), value = PropItem.Value.Text("Example"), action = null),
                PropItem(id = "4", title = PropItem.Title.Text("Some example"), value = PropItem.Value.TextRes(R.string.timer), action = null),
            )
        )
    }
}

data class PropItem(
    val id: String,
    val title: Title,
    val value: Value,
    val action: PropAction? = null
) {
    sealed interface Title {
        @JvmInline value class Text(val text: String) : Title
        @JvmInline value class ResId(@StringRes val id: Int) : Title

        @Composable
        fun asString(): String = when (this) {
            is Text -> text
            is ResId -> stringResource(id)
        }
    }

    sealed interface Value {
        data class Data<T : Number?>(val value: T, val format: @Composable ((T) -> String)? = null) : Value {
            @Composable
            override fun asString(): String = format?.invoke(value) ?: value.toString()
        }

        @JvmInline value class Text(val text: String) : Value {
            @Composable
            override fun asString(): String = text
        }

        @JvmInline value class TextRes(@StringRes val id: Int) : Value {
            @Composable
            override fun asString(): String = stringResource(id)
        }

        @Composable
        fun asString(): String
    }
}