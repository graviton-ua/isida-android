# Module: common:ui:metrox:viewmodel

ViewModel and navigation infrastructure integrated with Metro DI.

This module provides the glue between Jetpack Compose (Multiplatform) navigation, ViewModels, and the Metro dependency injection framework. It enables type-safe ViewModel injection within Composable functions across Android, iOS, and Desktop.

---

## 📊 KMP Migration and Platform Support Status

### Platform Support

| Platform | Supported | Notes |
| :--- | :---: | :--- |
| **Android** | ✅ | Fully supported. |
| **iOS** | ✅ | Fully supported via `commonMain`. |
| **Desktop (JVM)**| ✅ | Fully supported. |
| **Web (JS)** | ❌ | Not supported. |

---

## 📦 Core Functionality and Public API

This module exposes the following key components:

-   `InjectedViewModel`: A helper for retrieving a ViewModel instance from the Metro graph within a Composable.
-   `MetroViewModelFactory`: A factory that delegates ViewModel creation to Metro providers.
-   `ViewModelFactoryOwner`: An interface for components that can provide a ViewModel factory.
-   **Navigation**: Integration with `jetbrains-navigation-compose` for handling navigation events.

---

## 🔗 Dependencies

### Internal Dependencies

-   `:core:base`
-   `:core:logging`

### External Dependencies

-   `org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose`
-   `org.jetbrains.androidx.navigation:navigation-compose`

---

## 💡 Usage and Integration

Add as a dependency:

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation(projects.common.ui.metrox.viewmodel)
    }
}
```

### Example Usage

```kotlin
@Composable
fun MyScreen(
    viewModel: MyViewModel = injectedViewModel()
) {
    // ...
}
```
