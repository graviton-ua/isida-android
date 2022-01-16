package ua.graviton.isida.ui.home.prop

sealed class PropAction {
    data class SetPropDialog(
        val id: String,
    ) : PropAction()
}