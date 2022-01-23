package ua.graviton.isida.ui.setprop

sealed class SetPropAction {
    object NavigateUp : SetPropAction()
    object Send : SetPropAction()
}