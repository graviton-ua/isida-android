# Module: common:ui:compose-icons

Vector icon library for Whoppah's design system.

This module provides a centralized collection of SVG-based icons used throughout the application. It leverages Compose Multiplatform resources to ensure icons render crisply on Android, iOS, and Desktop.

---

## 📊 KMP Migration and Platform Support Status

### Platform Support

| Platform | Supported | Notes |
| :--- | :---: | :--- |
| **Android** | ✅ | Fully supported. |
| **iOS** | ✅ | Fully supported via Compose Multiplatform. |
| **Desktop (JVM)**| ✅ | Fully supported. |
| **Web (JS)** | ❌ | Not supported. |

---

## 📦 Core Functionality and Public API

-   `WhIcons`: The entry point for accessing icons.
-   **Categories**:
    -   `WhIcons.Action` (Search, Filter, Share)
    -   `WhIcons.Navigation` (Arrows, Menu, Close)
    -   `WhIcons.Social` (Facebook, Instagram)
    -   `WhIcons.Payment` (Credit Card, iDeal)
    -   `WhIcons.Category` (Furniture, Art, Lighting)

---

## 🔗 Dependencies

### External Dependencies

-   `org.jetbrains.compose.material:material-icons-extended` (Optional standard icons)

---

## 💡 Usage and Integration

Add as a dependency:

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation(projects.common.ui.composeIcons)
    }
}
```

### Example Usage

```kotlin
import com.whoppah.common.compose.icons.WhIcons

Icon(
    imageVector = WhIcons.Action.Search,
    contentDescription = "Search"
)
```
