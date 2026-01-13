# Module: core:preferences

Type-safe key–value preferences for application and user settings.

This module provides a cross-platform solution for persistent storage using Multiplatform Settings. It abstracts the underlying platform storage (SharedPreferences on Android, NSUserDefaults on iOS) into type-safe interfaces, enabling easy management of app flags, user sessions, and authentication tokens.

---

## 📊 KMP Migration and Platform Support Status

### Platform Support

| Platform | Supported | Notes |
| :--- | :---: | :--- |
| **Android** | ✅ | Fully supported via `SharedPreferences`. |
| **iOS** | ✅ | Fully supported via `NSUserDefaults`. |
| **Desktop (JVM)**| ✅ | Supported via `Properties` or in-memory. |
| **Web (JS)** | ❌ | Not supported. |

### Feature Migration Status

| Feature / Public API | Migration Status | Supported Targets | Notes / Blockers |
| :--- | :--- | :--- | :--- |
| `AppPreferences` | Migrated | Android, iOS, JVM | App-wide flags and settings. |
| `UserPreferences` | Migrated | Android, iOS, JVM | User-specific data and observable state. |
| `AuthToken` | Migrated | Android, iOS, JVM | Secure storage for authentication tokens. |
| Flow Observables | Migrated | Android, iOS, JVM | Reactive updates for preference changes. |

---

## 📦 Core Functionality and Public API

This module exposes the following key components:

-   `AppPreferences`: Interface for application-scoped settings (e.g., `showOnBoarding`, `advertiserId`).
-   `UserPreferences`: Interface for user-scoped data (e.g., `userId`, `authToken`). Supports observing changes via Kotlin `Flow`.
-   `AuthToken`: A sealed class representing various states of authentication tokens.
-   `PreferencesComponent`: Metro DI component for providing preference-related dependencies.

---

## 🔗 Dependencies

### Internal Dependencies

-   `:core:base`

### External Dependencies

This module uses the following external libraries:

-   `com.russhwolf:multiplatform-settings:1.3.0`
-   `com.russhwolf:multiplatform-settings-coroutines:1.3.0`

---

## 💡 Usage and Integration

To use this module, add it as a dependency in your `build.gradle.kts` file:

```kotlin
sourceSets {
    commonMain.dependencies {
        api(projects.core.preferences)
    }
}
```

### Example Usage

**Observing User Changes:**
```kotlin
class MyViewModel(private val userPreferences: UserPreferences) {
    fun observeUser() {
        userPreferences.observeUserId()
            .onEach { userId -> println("User changed to: $userId") }
            .launchIn(scope)
    }
}
```

**Saving App Settings:**
```kotlin
appPreferences.showOnBoarding = false
```

---

## 🌍 Used By

This module is a dependency for:

-   `:shared`
-   `:data:repo`
-   `:ui:auth:*`
-   `:ui:setup:*`
