package com.whoppah.common.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

/**
 * A platform-agnostic representation of a Locale.
 * On JVM/Android, this will be a typealias for `java.util.Locale`.
 * On iOS, this will be a typealias for `platform.Foundation.NSLocale`.
 */
expect class PlatformLocale

expect val PlatformLocale.countryCode: String
expect val PlatformLocale.language: String
expect val PlatformLocale.languageTag: String

/**
 * Gets the default locale for the current platform.
 */
expect fun platformLocaleGetDefault(): PlatformLocale

/**
 * Creates a PlatformLocale from a BCP 47 language tag (e.g., "en-US", "nl-NL").
 */
expect fun platformLocaleForLanguageTag(tag: String): PlatformLocale

@Composable @ReadOnlyComposable
expect fun defaultPlatformLocale(): PlatformLocale