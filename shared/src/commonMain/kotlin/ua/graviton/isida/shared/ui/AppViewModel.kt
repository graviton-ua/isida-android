package ua.graviton.isida.shared.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ua.graviton.isida.domain.interactors.SaveDataPackage

@Inject
@ViewModelKey(AppViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class AppViewModel(
    private val saveDataPackage: SaveDataPackage
) : ViewModel() {
    private val logger by lazy { Logger.withTag("AppViewModel") }

    fun submitData(bytes: ByteArray) {
        viewModelScope.parseAndSave(bytes)
    }

    fun submitStreamEnd() {
        viewModelScope.parseAndSave(null)
    }

    private fun CoroutineScope.parseAndSave(bytes: ByteArray?) = launch {
        try {
            saveDataPackage.executeSync(SaveDataPackage.Params(bytes))
        } catch (t: Throwable) {
            logger.w(t) { "Failed to save data package" }
        }
    }
}