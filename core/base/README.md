# Module: core:base

Fundamental utilities and shared base classes for the Isida application.

This module provides the foundational building blocks used across all other modules in the project. It centralizes platform-agnostic logic, common extensions, and application-wide contracts to ensure consistency and reduce code duplication in a Kotlin Multiplatform environment.

---

## 📊 KMP Migration and Platform Support Status

### Platform Support

| Platform | Supported | Notes |
| :--- | :---: | :--- |
| **Android** | ✅ | Fully supported and tested. |
| **iOS** | ✅ | Fully supported and tested via `commonMain`. |
| **Desktop (JVM)**| ⚠️ | Compiles, but not officially supported. |
| **Web (JS)** | ❌ | Not supported. |

### Feature Migration Status

| Feature / Public API | Migration Status | Supported Targets | Notes / Blockers |
| :--- | :--- | :--- | :--- |
| `InvokeStatus` | Migrated | Android, iOS | Sealed class representing async operation states. |
| `PlatformConfig` | Migrated | Android, iOS | Centralized configuration for different environments. |
| `AppInitializer` | Migrated | Android, iOS | Interface for module-specific initialization logic. |
| `AppCoroutineDispatchers` | Migrated | Android, iOS | Shared dispatcher provider for testing and injection. |
| Core Extensions | Migrated | Android, iOS | Collection of useful Kotlin extensions (String, Flow, etc.). |

---

## 📦 Core Functionality and Public API

This module exposes the following key components:

-   `InvokeStatus`: Represents the lifecycle of an asynchronous operation (`Started`, `Success`, `Error`).
-   `PlatformConfig`: A data class containing environment-specific configurations (API URLs, tokens, flags).
-   `PlatformInfo`: Provides information about the current platform (type, OS version).
-   `AppInitializer`: A functional interface for components that need to be initialized at app startup.
-   `AppCoroutineDispatchers`: Encapsulates Coroutine dispatchers (`io`, `computation`, `main`) for consistent threading management.
-   **Extensions**: A rich set of extensions for `String`, `Flow` (Combine), and general utility (`LetIf`, `Lazy`).

---

## 🔗 Dependencies

### Internal Dependencies

This module has no internal dependencies on other project modules.

### External Dependencies

This module uses the following external libraries:

-   `org.jetbrains.kotlinx:kotlinx-coroutines-core`
-   `org.jetbrains.kotlinx:kotlinx-atomicfu`

---

## 💡 Usage and Integration

To use this module, add it as a dependency in your `build.gradle.kts` file:

```kotlin
sourceSets {
    commonMain.dependencies {
        api(projects.core.base)
    }
}
```

### Example Usage

**Using InvokeStatus:**
```kotlin
fun fetchData(): Flow<InvokeStatus> = flow {
    emit(InvokeStatus.Started)
    try {
        val result = api.call()
        emit(InvokeStatus.Success(result))
    } catch (e: Exception) {
        emit(InvokeStatus.Error(e))
    }
}
```

**Using AppInitializer:**
```kotlin
class MyModuleInitializer : AppInitializer {
    override fun init() {
        // Module specific startup logic
    }
}
```

---

## 🌍 Used By

This module is a foundation for almost all other modules in the project, including:

-   `:core:logging`
-   `:core:preferences`
-   `:core:datetime`
-   `:data:*`
-   `:shared`
