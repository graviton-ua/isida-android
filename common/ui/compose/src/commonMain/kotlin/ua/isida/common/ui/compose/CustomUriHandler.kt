package ua.isida.common.ui.compose

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler

/**
 * A composition local value that provides the current [UriHandler] instance. This is typically used to
 * access the [UriHandler] created by the application, ensuring consistent behavior throughout the
 * composition hierarchy.
 * This used as addition to default [LocalUriHandler] but needed only for Dialogs
 * When dialog rendered for some reason system provide each time new instance of [LocalUriHandler]
 */
val LocalWhUriHandler = staticCompositionLocalOf<UriHandler?> { null }