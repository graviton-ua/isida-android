package com.whoppah.common.resources

import java.util.Locale

// Actual implementation for JVM and Android using java.util.Locale
internal actual fun getCountryDisplayName(countryCode: String): String {
    // Locale("", countryCode) creates a locale for a specific country,
    // and getDisplayCountry() translates it to the device's default language.
    return Locale("", countryCode).getDisplayCountry(Locale.getDefault())
}