package com.whoppah.common.resources

import platform.Foundation.NSLocale
import platform.Foundation.NSLocaleCountryCode
import platform.Foundation.currentLocale

// Actual implementation for iOS using NSLocale
internal actual fun getCountryDisplayName(countryCode: String): String {
    return NSLocale.currentLocale.displayNameForKey(NSLocaleCountryCode, countryCode)
        ?: countryCode // Fallback to the code itself if the name isn't found
}