package com.whoppah.common.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalInspectionMode
import java.util.Locale

actual typealias PlatformLocale = Locale

actual val PlatformLocale.countryCode: String
    get() = country

actual val PlatformLocale.language: String
    get() = language

actual val PlatformLocale.languageTag: String
    get() = toLanguageTag()

actual fun platformLocaleGetDefault(): PlatformLocale = Locale.getDefault()

actual fun platformLocaleForLanguageTag(tag: String): PlatformLocale = Locale.forLanguageTag(tag)

@Composable @ReadOnlyComposable
actual fun defaultPlatformLocale(): PlatformLocale {
    // Check if we are in a @Preview
    if (LocalInspectionMode.current) {
        // Provide a stable, fake locale for previews
        return Locale.US // Or Locale.getDefault(), Locale("en"), etc.
    }

    // This is the runtime-only code
    val locales = LocalConfiguration.current.locales

    // Add a safety check for runtime as well
    return if (locales.isEmpty) Locale.getDefault() else locales[0]
}