package ua.graviton.isida.domain

import kotlinx.coroutines.flow.MutableStateFlow

object DeviceConnectionHolder {
    val isConnected = MutableStateFlow(false)
}