package ua.graviton.isida.ui.setprop

sealed class SetPropViewState(val id: String) {
    /**
     *  State used mostly as initial state with empty body for dialog window, no data yet loaded no errors yet happen
     */
    object Empty : SetPropViewState("empty")

    /**
     *  State represents situation when data wasn't able to load, so device didn't provide us any data yet
     */
    object NoData : SetPropViewState("no_data")

    /**
     *  State represents situation when property wasn't found in device data, should not be happen at all
     */
    object NotFound : SetPropViewState("not_found")

    /**
     *  Success state for dialog with property data and property state for future updates
     */
    data class Success(
        val propId: String,
        val inputState: InputState,
    ) : SetPropViewState("success")


    sealed interface InputState {
        object Unknown : InputState
        @JvmInline value class NumberInput(val value: String) : InputState
//        @JvmInline value class IntProperty(val value: Int) : InputState
//        @JvmInline value class FloatProperty(val value: Float) : InputState
    }

    companion object {
        val Init = Empty
        val PreviewSuccess = Success(
            propId = "Some property id here",
            inputState = InputState.NumberInput("12"),
        )
    }
}