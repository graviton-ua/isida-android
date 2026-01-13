package com.whoppah.metrox.viewmodel

import androidx.lifecycle.ViewModelProvider

interface ViewModelFactoryOwner {
    
    val viewModelFactory: ViewModelProvider.Factory
}