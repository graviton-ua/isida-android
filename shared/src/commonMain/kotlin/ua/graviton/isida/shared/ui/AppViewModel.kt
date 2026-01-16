package ua.graviton.isida.shared.ui

import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import com.whoppah.metrox.viewmodel.ViewModelKey
import com.whoppah.metrox.viewmodel.ViewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject

@Inject
@ViewModelKey(AppViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
class AppViewModel : ViewModel() {
    private val logger by lazy { Logger.withTag("AppViewModel") }
}