package ua.graviton.isida.ui.setprop

sealed class SetPropAction {
    object NavigateUp : SetPropAction()
    object Send : SetPropAction()

    data class Update(val state: UpdateState) : SetPropAction()

    sealed class UpdateState {
        data class NumberInput(val value: String) : UpdateState()
    }
}