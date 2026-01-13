package com.whoppah.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import platform.UIKit.UIApplication
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.setStatusBarStyle

@Composable
actual fun SystemAppearance(isDarkTheme: Boolean) {
    DisposableEffect(isDarkTheme) {
        val application = UIApplication.sharedApplication

        // Capture logic isn't as straightforward on iOS without a VC reference,
        // but we can assume the default for your app is DarkContent (Dark icons on Light bg).
        val originalStyle = UIStatusBarStyleDarkContent

        // Apply Style
        // Dark Theme (Gallery) -> LightContent (White text)
        // Light Theme (App) -> DarkContent (Black text)
        val targetStyle = if (isDarkTheme) UIStatusBarStyleLightContent else UIStatusBarStyleDarkContent

        // Note: setStatusBarStyle is deprecated in newer iOS versions in favor of
        // specific ViewController overrides, but works for simple KMP setups.
        // For a robust solution, you'd bind this to your ComposeUIViewController.
        application.setStatusBarStyle(targetStyle, animated = true)

        onDispose {
            application.setStatusBarStyle(originalStyle, animated = true)
        }
    }
}