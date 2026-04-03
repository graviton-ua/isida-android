package ua.isida.ui.home.audit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.isida.core.logging.FileLogManager
import ua.isida.metrox.viewmodel.ViewModelKey
import ua.isida.metrox.viewmodel.ViewModelScope

@Inject
@ViewModelKey(AuditLogViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class AuditLogViewModel(
    private val logManager: FileLogManager,
) : ViewModel() {
    private val _logs = MutableStateFlow<List<String>>(emptyList())
    val logs: StateFlow<List<String>> = _logs.asStateFlow()

    init {
        loadLogs()
    }

    private fun loadLogs() {
        viewModelScope.launch {
            _logs.value = logManager.readRecentLogs()
        }
    }

    fun exportLogs() {
        logManager.shareLatestLog()
    }
}
