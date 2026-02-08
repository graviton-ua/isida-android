package ua.graviton.isida.ui.properties.types

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.whoppah.common.compose.input.DefaultNumberInputTextFieldStateHelper
import com.whoppah.common.compose.input.InputTextFieldStateErrorScope
import com.whoppah.common.compose.input.NumberInputTextFieldState.Error
import com.whoppah.common.compose.input.PriceInputTransformation
import com.whoppah.common.compose.ui.WhTextField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.graviton.isida.ui.properties.DeviceProperty

@Stable
internal abstract class NumberInputTextFieldDeviceProperty<T : Number>(
    initValue: T? = null,
    override val title: @Composable () -> String,
    override val description: (@Composable () -> String)? = null,
    private val allowDecimals: Boolean = false,
    onValidate: InputTextFieldStateErrorScope<Error>.(String) -> Error? = { null },
) : DeviceProperty {
    protected val inputHelper = DefaultNumberInputTextFieldStateHelper(initValue = initValue, onValidate = onValidate)

    @Composable
    override fun Content(modifier: Modifier) {
        WhTextField(
            state = inputHelper.state,
            inputTransformation = PriceInputTransformation(allowDecimals = allowDecimals),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = modifier,
        )
    }

    override val isValid: Flow<Boolean> = inputHelper.errorFlow.map { it == null }
    override fun validate(): Boolean = inputHelper.validate()?.let { false } ?: true

    override suspend fun validateOnInputUpdate() = inputHelper.validateOnInputUpdate()
    override suspend fun clearErrorOnInputUpdate() = inputHelper.clearErrorOnInputUpdate()
}