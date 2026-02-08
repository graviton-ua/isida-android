package ua.graviton.isida.ui.properties

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow
import ua.graviton.isida.data.protocol.packets.StatusPacket

@Stable
interface DeviceProperty {
    val title: @Composable () -> String
    val description: (@Composable () -> String)?

    @Composable
    fun Content(modifier: Modifier)

    val isValid: Flow<Boolean>
    fun validate(): Boolean

    suspend fun validateOnInputUpdate()

    suspend fun clearErrorOnInputUpdate()

    fun readValue(packet: StatusPacket)
    fun copyAndUpdate(packet: StatusPacket): StatusPacket
}