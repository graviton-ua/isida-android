package ua.graviton.isida.ui.setprop

import androidx.compose.runtime.Stable
import ua.graviton.isida.ui.setprop.models.DeviceProperty

@Stable
data class SetPropViewState(
    val property: DeviceProperty,
    val waitingForData: Boolean = true,
) {
    // /**
    //  *  State used mostly as initial state with empty body for dialog window, no data yet loaded no errors yet happen
    //  */
    // object Empty : SetPropViewState("empty")
    //
    // /**
    //  *  State represents situation when data wasn't able to load, so device didn't provide us any data yet
    //  */
    // object NoData : SetPropViewState("no_data")
    //
    // /**
    //  *  State represents situation when property wasn't found in device data, should not be happen at all
    //  */
    // object NotFound : SetPropViewState("not_found")
    //
    // /**
    //  *  Success state for dialog with property data and property state for future updates
    //  */
    // data class Success(
    //     val property: DeviceProperty<*>,
    // ) : SetPropViewState("success")
}