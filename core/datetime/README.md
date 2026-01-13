# Module: core:datetime

Cross-platform date and time utilities powered by Kotlinx Datetime.

This module centralizes time-related helpers, extensions, and abstractions to ensure consistent handling of instants, local dates, times, and time zones across all platforms. It simplifies complex datetime arithmetic and formatting.

---

## 📊 KMP Migration and Platform Support Status

### Platform Support

| Platform | Supported | Notes |
| :--- | :---: | :--- |
| **Android** | ✅ | Fully supported via `commonMain`. |
| **iOS** | ✅ | Fully supported via `commonMain`. |
| **Desktop (JVM)**| ⚠️ | Compiles, but not officially tested. |
| **Web (JS)** | ❌ | Not supported. |

### Feature Migration Status

| Feature / Public API | Migration Status | Supported Targets | Notes / Blockers |
| :--- | :--- | :--- | :--- |
| `LocalDate` Extensions | Migrated | Android, iOS | Helpers for formatting and arithmetic. |
| `LocalDateTime` Extensions | Migrated | Android, iOS | Consistent cross-platform formatting. |
| `LocalTime` Extensions | Migrated | Android, iOS | Time-specific utilities. |

---

## 📦 Core Functionality and Public API

This module exposes the following key components:

-   **LocalDate Extensions**: Utilities for manipulating and formatting `LocalDate` objects.
-   **LocalDateTime Extensions**: Enhanced support for `LocalDateTime` operations including platform-agnostic formatting.
-   **LocalTime Extensions**: Helpers for working with time-only values.

---

## 🔗 Dependencies

### Internal Dependencies

This module has no internal dependencies on other project modules.

### External Dependencies

This module uses the following external libraries:

-   `org.jetbrains.kotlinx:kotlinx-datetime`

---

## 💡 Usage and Integration

To use this module, add it as a dependency in your `build.gradle.kts` file:

```kotlin
sourceSets {
    commonMain.dependencies {
        api(projects.core.datetime)
    }
}
```

### Example Usage

```kotlin
import com.whoppah.datetime.format
import kotlinx.datetime.LocalDateTime

val now = LocalDateTime(2024, 1, 5, 12, 0)
val formatted = now.format("dd/MM/yyyy")
```

---

## 🌍 Used By

This module is used by:

-   `:data:models`
-   `:shared`
-   `:ui:cart`
-   `:ui:checkout`
