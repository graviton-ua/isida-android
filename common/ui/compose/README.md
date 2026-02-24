# Module: common:ui:compose

Reusable Jetpack Compose UI components and theming for the Whoppah application.

This module provides the design system foundation, including the custom theme, typography, colors, and a comprehensive set of "Wh"-prefixed components (buttons, text fields, dialogs, etc.). It ensures visual consistency across the entire app and works on both Android and iOS via Compose Multiplatform.

---

## 📊 KMP Migration and Platform Support Status

### Platform Support

| Platform | Supported | Notes |
| :--- | :---: | :--- |
| **Android** | ✅ | Fully supported. |
| **iOS** | ✅ | Fully supported via Compose Multiplatform. |
| **Desktop (JVM)**| ✅ | Supported. |
| **Web (JS)** | ❌ | Not supported. |

### Feature Migration Status

| Feature / Public API | Migration Status | Supported Targets | Notes / Blockers |
| :--- | :--- | :--- | :--- |
| Design System | Migrated | Android, iOS | `WhTheme`, `WhTypography`, `WhColors`. |
| Core Components | Migrated | Android, iOS | `WhButton`, `WhTextField`, `WhScaffold`. |
| Navigation | Migrated | Android, iOS | `jetbrains-navigation` integration. |
| Image Loading | Migrated | Android, iOS | `coil-compose` integration. |

---

## 📦 Core Functionality and Public API

This module exposes the Whoppah Design System:

-   **Theming**: `WhTheme` wrapper providing the app's visual identity.
-   **Components**:
    -   `WhButton`, `WhIconButton`, `WhFab`
    -   `WhTextField`, `WhSearchField`
    -   `WhTopAppBar`, `WhScaffold`, `WhBottomSheet`
    -   `WhCheckbox`, `WhRadioButton`, `WhSwitch`
    -   `WhTag`, `WhBadge`, `WhChip`
    -   `WhDialog`, `WhModal`
-   **Utilities**: HTML conversion helpers, keyboard handling, and modifier extensions.

---

## 🔗 Dependencies

### Internal Dependencies

-   `:core:base`
-   `:core:datetime`
-   `:core:logging`
-   `:common:ui:compose-icons` (Icons)
-   `:common:ui:resources` (Strings/Drawables)
-   `:common:ui:phone-number`

### External Dependencies

This module uses the following external libraries:

-   **Compose Multiplatform**: `foundation`, `material3`, `ui`, `runtime`, `animation`
-   **Coil**: `coil-compose` for image loading
-   **Navigation**: `jetbrains-navigation-compose`
-   **Lifecycle**: `jetbrains-lifecycle-viewmodel-compose`

---

## 💡 Usage and Integration

To use this module, add it as a dependency in your `build.gradle.kts` file:

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation(projects.common.ui.compose)
    }
}
```

### Example Usage

```kotlin
import ua.isida.common.ui.compose.theme.WhTheme
import ua.isida.common.ui.compose.ui.WhButton

@Composable
fun MyScreen() {
    WhTheme {
        WhButton(onClick = { /* ... */ }) {
            Text("Click Me")
        }
    }
}
```

---

## 🌍 Used By

This module is the UI foundation for:

-   `androidApp`
-   `shared`
-   All Feature UI modules (`:ui:*`)
