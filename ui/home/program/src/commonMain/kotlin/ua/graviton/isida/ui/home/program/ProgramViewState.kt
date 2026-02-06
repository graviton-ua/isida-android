package ua.graviton.isida.ui.home.program

data class ProgramViewState(
    val cellNumber: Int = 0,
    val header: String,
    val items: List<String>,
) {
    companion object {
        val Empty = ProgramViewState(
            header = "",
            items = emptyList(),
        )
    }
}