package ua.isida.ui.logreport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.io.files.SystemFileSystem
import ua.isida.util.FileSharer
import ua.isida.util.PathProvider

@Inject
@ViewModelKey(LogReportViewModel::class)
@ContributesIntoMap(AppScope::class)
internal class LogReportViewModel(
    private val pathProvider: PathProvider,
    private val fileSharer: FileSharer
) : ViewModel() {
    private val _files = MutableStateFlow<List<LogFileInfo>>(emptyList())
    val files: StateFlow<List<LogFileInfo>> = _files.asStateFlow()

    init {
        Logger.i { "User opened Log Report screen. Refreshing file list..." }
        loadFiles()
    }

    private fun loadFiles() {
        viewModelScope.launch {
            val logsDir = pathProvider.logsPath
            try {
                if (SystemFileSystem.exists(logsDir)) {
                    val fileList = SystemFileSystem.list(logsDir)
                        .filter { it.name.startsWith("logs_") && it.name.endsWith(".log") }
                        .map { path ->
                            val metadata = SystemFileSystem.metadataOrNull(path)
                            LogFileInfo(
                                path = path,
                                name = path.name,
                                sizeBytes = metadata?.size ?: 0L
                            )
                        }
                        .sortedByDescending { it.name }
                    _files.value = fileList
                }
            } catch (e: Exception) {
                println("Failed to load log files: ${e.message}")
            }
        }
    }

    fun toggleSelection(info: LogFileInfo) {
        _files.value = _files.value.map {
            if (it.path == info.path) it.copy(isSelected = !it.isSelected) else it
        }
    }

    fun shareSelectedFiles() {
        val selected = _files.value.filter { it.isSelected }.map { it.path }
        if (selected.isNotEmpty()) {
            fileSharer.shareFiles(selected, "App Log Export")
        }
    }
}
