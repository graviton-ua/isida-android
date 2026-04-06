package ua.isida.shared.ui

import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey

@Inject
@ViewModelKey(AppViewModel::class)
@ContributesIntoMap(AppScope::class)
class AppViewModel : ViewModel() {
    private val logger by lazy { Logger.withTag("AppViewModel") }
}