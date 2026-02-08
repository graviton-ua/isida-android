package ua.graviton.isida.ui.home.prop

import androidx.compose.runtime.Immutable
import com.whoppah.common.resources.ComposableString
import com.whoppah.common.resources.ComposableString.Companion.composableString
import com.whoppah.common.resources.Res
import com.whoppah.common.resources.timer
import org.jetbrains.compose.resources.stringResource

@Immutable
data class PropViewState(
    val deviceConnected: Boolean,
    val items: List<PropItem>
) {
    companion object {
        val Init = PropViewState(deviceConnected = false, items = emptyList())
        val Preview = PropViewState(
            deviceConnected = true,
            items = listOf(
                PropItem.Default(id = "1", title = composableString { "Some item example" }, value = composableString { "23 C" }),
                PropItem.Default(id = "2", title = composableString { "item example" }, value = composableString { "23.2333" }),
                PropItem.Default(id = "3", title = composableString { "Some item" }, value = composableString { "Example" }),
                PropItem.Default(
                    id = "4",
                    title = composableString { "Some example" },
                    value = composableString { stringResource(Res.string.timer) }),
            )
        )
    }
}