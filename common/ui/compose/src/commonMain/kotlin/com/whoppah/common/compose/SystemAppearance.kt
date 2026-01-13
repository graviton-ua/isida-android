package com.whoppah.common.compose

import androidx.compose.runtime.Composable

/**
 * Manages the System UI appearance (Status bar and Navigation bar).
 *
 * @param isDarkTheme If true, requests light icons (for dark backgrounds).
 * If false, requests dark icons (for light backgrounds).
 */
@Composable
expect fun SystemAppearance(isDarkTheme: Boolean)