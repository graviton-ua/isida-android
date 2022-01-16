package ua.graviton.isida.ui.home

sealed class HomeAction {
    object ConnectDevice : HomeAction()
    object DisconnectDevice : HomeAction()
    object OpenPowerDialog : HomeAction()
    data class OpenSetPropDialog(val id: String) : HomeAction()
}