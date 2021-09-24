package ua.graviton.isida.ui.report

data class ReportViewState(
    val cellNumber: Int = 0
) {
    companion object {
        val Empty = ReportViewState()
    }
}
