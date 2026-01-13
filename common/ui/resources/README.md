# :common:ui:resources

## Module Overview

The `:common:ui:resources` module is an Android library that centralizes all shared UI resources for the application. This includes strings, colors, dimensions, drawables, fonts, and styles that are used across multiple feature modules.

A key responsibility of this module is managing localized strings, which are synchronized from the [Lokalise](https://lokalise.com/) platform via a custom Gradle task.

By consolidating resources here, we ensure consistency, simplify maintenance, and make it easy to manage localization.

### Platform Support

| Platform | Supported | Notes |
| :--- | :---: | :--- |
| **Android** | ✅ | Fully supported. |
| **iOS** | ✅ | Fully supported. Resources are bundled in the framework. |
| **Desktop (JVM)**| ✅ | Fully supported. |
| **Web (JS)** | ❌ | Not supported. |

---

## Core Functionality and Public API

-   **Strings**: Localized strings accessible via `Res.string.my_string`.
-   **Images**: Shared vector and raster images via `Res.drawable.my_image`.
-   **Fonts**: Custom application fonts via `Res.font.my_font`.
-   **Colors**: Shared color palette via `Res.color.my_color`.

---

## Dependencies

### External Dependencies

-   `org.jetbrains.compose.resources` (Official Compose Multiplatform Resources)

---

## Usage and Integration

Add as a dependency:

```kotlin
sourceSets {
    commonMain.dependencies {
        api(projects.common.ui.resources)
    }
}
```

### Example Usage

```kotlin
import com.whoppah.common.resources.Res
import com.whoppah.common.resources.app_name
import org.jetbrains.compose.resources.stringResource

@Composable
fun MyComponent() {
    Text(text = stringResource(Res.string.app_name))
}
```

## Module Dependencies

This module has no internal dependencies on other project modules.
