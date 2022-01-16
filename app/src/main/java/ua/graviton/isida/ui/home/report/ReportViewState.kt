package ua.graviton.isida.ui.home.report

data class ReportViewState(
    val cellNumber: Int = 0,
    val header: String,
    val items: List<String>,
) {
    companion object {
        val Empty = ReportViewState(
            header = "",
            items = emptyList(),
        )
    }
}
