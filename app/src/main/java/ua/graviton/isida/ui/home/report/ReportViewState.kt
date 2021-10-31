package ua.graviton.isida.ui.home.report

data class ReportViewState(
    val cellNumber: Int = 0
) {
    companion object {
        val Empty = ReportViewState()
    }
}
