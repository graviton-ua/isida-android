package ua.graviton.isida.ui.properties.types

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.whoppah.common.compose.input.DefaultInputStateHelper
import com.whoppah.common.compose.input.InputState
import com.whoppah.common.compose.input.InputStateErrorScope
import com.whoppah.common.compose.theme.WhoppahTheme
import com.whoppah.common.compose.ui.WhRadioButton
import com.whoppah.common.resources.Res
import com.whoppah.common.resources.programm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.stringResource
import ua.graviton.isida.ui.properties.DeviceProperty

@Stable
abstract class RadioListDeviceProperty<T>(
    initValue: T? = null,
    val list: List<T>,
    override val title: @Composable () -> String,
    override val description: (@Composable () -> String)? = null,
    val listItemTitleMap: @Composable (T) -> String = { "Item $it" },
    onValidate: InputStateErrorScope<Error>.(T?) -> Error? = { if (it == null) Error.Required else null },
) : DeviceProperty {
    val inputHelper = DefaultInputStateHelper(
        initValue = initValue,
        onValidate = onValidate,
        errorScope = RadioErrorScope,
    )

    @Composable
    override fun Content(modifier: Modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier.width(IntrinsicSize.Min)
        ) {
            list.forEach { item ->
                ItemList(
                    item = item,
                    title = listItemTitleMap(item),
                    isSelected = inputHelper.value == item,
                    onClick = { inputHelper.setValue(item) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        if (inputHelper.state.isError)
            Text(
                text = inputHelper.state.errorState.value?.asLabel() ?: "",
                style = WhoppahTheme.typography.helper,
                color = WhoppahTheme.colors.error,
            )
    }

    @Composable
    open fun ItemList(
        item: T,
        title: String,
        isSelected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        WhRadioButton(
            selected = isSelected,
            onClick = onClick,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier,
        ) { Text(text = title) }
    }

    override val isValid: Flow<Boolean> = inputHelper.errorFlow.map { it == null }
    override fun validate(): Boolean = inputHelper.validate()?.let { false } ?: true

    override suspend fun validateOnInputUpdate() = inputHelper.validateOnInputUpdate()
    override suspend fun clearErrorOnInputUpdate() = inputHelper.clearErrorOnInputUpdate()

    interface Error : InputState.Error {
        object Required : Error {
            @Composable
            override fun asLabel(): String = "Required"
        }

        data class Custom(private val onMessage: @Composable () -> String) : Error {
            constructor(message: String) : this(onMessage = { message })

            @Composable
            override fun asLabel(): String = onMessage()
        }
    }

    object RadioErrorScope : InputStateErrorScope<Error> {
        override fun error(onMessage: @Composable () -> String): Error = Error.Custom(onMessage)
    }
}


private class RadioListPreviewParameterProvider : PreviewParameterProvider<DeviceProperty> {

    @Stable
    private object TestEmpty : RadioListDeviceProperty<Int>(
        initValue = null,
        title = { "RelayMode" },
        list = listOf(1, 2, 3, 4, 5),
        listItemTitleMap = { "Example of item title $it" },
        onValidate = {
            if (it == null) error { "Custom required error" } else null
        },
    )

    @Stable
    private object TestHasValue : RadioListDeviceProperty<Int>(
        initValue = 3,
        title = { "RelayMode" },
        list = listOf(1, 2, 3, 4, 5),
        listItemTitleMap = { it ->
            when (it) {
                1 -> "Title asdasd"
                2 -> "asdasd"
                else -> stringResource(Res.string.programm) + " asdasd "
            }
        },
    )

    val properties = listOf<DeviceProperty>(
        TestEmpty, TestHasValue,
    )
    override val values = properties.asSequence()
}

// @Preview
// @Composable
// private fun Preview(
//     @PreviewParameter(RadioListPreviewParameterProvider::class) property: DeviceProperty,
// ) {
//     WhoppahTheme {
//         LaunchedEffect(Unit) { property.validate() }
//         SetPropDialog(
//             state = SetPropViewState(property = property),
//             navigateUp = {},
//             send = {},
//         )
//     }
// }