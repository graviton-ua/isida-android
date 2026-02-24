package ua.isida.common.ui.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import ua.isida.common.ui.resources.PlatformLocale
import ua.isida.common.ui.resources.language
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
actual fun defaultPlatformLocale(): PlatformLocale = Locale.getDefault()