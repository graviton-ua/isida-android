package ua.crypto.shared.inject

import androidx.lifecycle.ViewModelProvider
import ua.crypto.ui.di.ViewModelGraph

interface AppGraph : ViewModelGraph.Factory, SharedApplicationComponent {
    val viewModelFactory: ViewModelProvider.Factory
}