# Module: core:logging

Standardized logging and crash reporting for the Whoppah application.

This module provides a unified logging interface built on Kermit, with integrated crash reporting via Firebase Crashlytics for Android and iOS. It ensures consistent log levels and crash capture across both platforms in a Kotlin Multiplatform environment.

---

## 📊 KMP Migration and Platform Support Status

### Platform Support

| Platform | Supported | Notes |
| :--- | :---: | :--- |
| **Android** | ✅ | Fully supported with Firebase Crashlytics. |
| **iOS** | ✅ | Fully supported with Crashlytics via Crashkios. |
| **Desktop (JVM)**| ✅ | Supported with standard console logging. |
| **Web (JS)** | ❌ | Not supported. |

### Feature Migration Status

| Feature / Public API | Migration Status | Supported Targets | Notes / Blockers |
| :--- | :--- | :--- | :--- |
| Kermit Logging | Migrated | Android, iOS, JVM | Severity levels configured per build type. |
| Crashlytics Reporting | Migrated | Android, iOS | Integrated via platform-specific initializers. |
| Crash Toggle | Migrated | Android, iOS | Runtime enable/disable of crash reporting. |

---

## 📦 Core Functionality and Public API

This module exposes the following key components:

-   `KermitInitializer`: Configures Kermit with appropriate log writers and severity levels based on build configuration.
-   `CrashReportingInitializer`: Centralized entry point for initializing crash reporting across platforms.
-   `SetCrashReportingEnabledAction`: Interface to toggle crash reporting state at runtime.
-   `LoggerComponent`: Metro DI component for providing logging-related dependencies.
-   `CrashlyticsLoggerWriter`: Custom Kermit writer that forwards logs to Crashlytics as custom keys or breadcrumbs.

---

## 🔗 Dependencies

### Internal Dependencies

-   `:core:base`

### External Dependencies

This module uses the following external libraries:

-   `co.touchlab:kermit:2.0.8`
-   `co.touchlab.crashkios:crashlytics:0.9.0`
-   `com.google.firebase:firebase-crashlytics` (Android)

---

## 💡 Usage and Integration

To use this module, add it as a dependency in your `build.gradle.kts` file:

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation(projects.core.logging)
    }
}
```

### Example Usage

**Logging with Kermit:**
```kotlin
import co.touchlab.kermit.Logger

Logger.d { "This is a debug log" }
Logger.e(exception) { "An error occurred" }
```

**Initializing during startup:**
```kotlin
// In your platform-specific application class
KermitInitializer().init()
CrashReportingInitializer().init()
```

---

## 🌍 Used By

This module is a core dependency for:

-   `androidApp`
-   `shared`
-   `data:*`
-   `ui:*`
