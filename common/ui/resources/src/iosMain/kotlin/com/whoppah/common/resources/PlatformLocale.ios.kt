package com.whoppah.common.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import platform.Foundation.*
import platform.Foundation.countryCode

actual typealias PlatformLocale = NSLocale

/**
 * Gets the ISO 3166-1 alpha-2 country code for this locale.
 * Returns an empty string if the country code is not available.
 */
actual val PlatformLocale.countryCode: String
    get() = this.countryCode ?: this.regionCode ?: ""

actual val PlatformLocale.language: String
    get() = this.languageCode

/**
 * Returns the BCP 47 compliant language tag.
 * iOS `localeIdentifier` usually returns "en_US", but BCP 47 expects "en-US".
 * We replace underscores with hyphens for consistency with the Android/JVM implementation.
 */
actual val PlatformLocale.languageTag: String
    get() = this.localeIdentifier.replace('_', '-')

/**
 * Gets the default locale for the current platform.
 */
actual fun platformLocaleGetDefault(): PlatformLocale = NSLocale.currentLocale

/**
 * Creates a PlatformLocale from a BCP 47 language tag (e.g., "en-US", "nl-NL").
 */
actual fun platformLocaleForLanguageTag(tag: String): PlatformLocale =
    NSLocale.localeWithLocaleIdentifier(tag)

@Composable
@ReadOnlyComposable
actual fun defaultPlatformLocale(): PlatformLocale {
    // On iOS, we generally trust NSLocale.currentLocale to provide the
    // system configuration. Unlike Android, we don't usually need to
    // look up a specific LocalConfiguration for the Locale.
    return NSLocale.currentLocale
}