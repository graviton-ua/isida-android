package ua.graviton.isida.ui.navigation

import androidx.navigation3.runtime.NavKey

interface Navigator {
    fun navigateUp()

    fun navigateTo(key: NavKey)
}