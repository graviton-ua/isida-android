package ua.graviton.isida.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import ua.graviton.isida.domain.interactors.SaveDataPackage
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val saveDataPackage: SaveDataPackage
) : ViewModel() {

    fun submitData(bytes: ByteArray) {
        viewModelScope.parseAndSave(bytes)
    }

    private fun CoroutineScope.parseAndSave(bytes: ByteArray) = launch {
        try {
            saveDataPackage.executeSync(SaveDataPackage.Params(bytes))
        } catch (t: Throwable) {
            Timber.w(t)
        }
    }
}