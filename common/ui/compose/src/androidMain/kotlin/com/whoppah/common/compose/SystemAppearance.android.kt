package com.whoppah.common.compose

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
actual fun SystemAppearance(isDarkTheme: Boolean) {
    val view = LocalView.current

    // Create a controller for the window
    val window = (view.context as? Activity)?.window ?: return
    val insetsController = remember(window, view) {
        WindowCompat.getInsetsController(window, view)
    }

    DisposableEffect(insetsController, isDarkTheme) {
        // 1. Capture the original state so we can restore it later
        val originalStatusBars = insetsController.isAppearanceLightStatusBars
        val originalNavBars = insetsController.isAppearanceLightNavigationBars

        // 2. Apply the requested state
        // If isDarkTheme is true (Gallery), we want Light Icons (isAppearanceLight... = false)
        // If isDarkTheme is false (Main App), we want Dark Icons (isAppearanceLight... = true)
        insetsController.isAppearanceLightStatusBars = !isDarkTheme
        insetsController.isAppearanceLightNavigationBars = !isDarkTheme

        // 3. Restore state when this Composable leaves the screen
        onDispose {
            insetsController.isAppearanceLightStatusBars = originalStatusBars
            insetsController.isAppearanceLightNavigationBars = originalNavBars
        }
    }
}