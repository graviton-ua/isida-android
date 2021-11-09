package ua.graviton.isida.ui.home

sealed class HomeAction {
    object ConnectDevice : HomeAction()
    object DisconnectDevice : HomeAction()
}