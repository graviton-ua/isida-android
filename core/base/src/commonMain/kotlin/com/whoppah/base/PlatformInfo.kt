package com.whoppah.base

/**
 * A platform-agnostic data class holding information about the current device and OS.
 * This object is expected to be provided via Dependency Injection from the platform-specific
 * application module (:app, :iosApp, etc.).
 */
data class PlatformInfo(
    val userDevice: String,       // e.g., "Google Pixel 8" or "iPhone 15 Pro"
    val osVersion: String,        // e.g., "14" or "17.1"
    val appIdentifier: String     // e.g., "android" or "ios"
)