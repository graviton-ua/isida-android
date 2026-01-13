# Module: common:ui:permissions

Cross-platform permission handling for Jetpack Compose.

This module provides a unified API for requesting and managing runtime permissions (Camera, Notifications, Storage) in a Compose Multiplatform environment. It abstracts away the platform-specific implementation details using `moko-permissions`.

---

## 📊 KMP Migration and Platform Support Status

### Platform Support

| Platform | Supported | Notes |
| :--- | :---: | :--- |
| **Android** | ✅ | Fully supported via `moko-permissions`. |
| **iOS** | ✅ | Fully supported via `moko-permissions`. |
| **Desktop (JVM)**| ⚠️ | Stub implementations (permissions always granted/denied). |
| **Web (JS)** | ❌ | Not supported. |

---

## 📦 Core Functionality and Public API

-   `PermissionGuard`: A Composable wrapper that handles the permission request flow. It renders different content based on whether the permission is granted, denied, or needs rationale.
-   `PermissionState`: Represents the current status of a permission (Granted, Denied, NotDetermined).
-   **Supported Permissions**:
    -   Camera
    -   Notifications
    -   Storage (Gallery/Files)

---

## 🔗 Dependencies

### Internal Dependencies

-   `:core:base`
-   `:core:logging`

### External Dependencies

-   `dev.icerock.moko:permissions` (Cross-platform permissions)
-   `dev.icerock.moko:permissions-compose`

---

## 💡 Usage and Integration

Add as a dependency:

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation(projects.common.ui.permissions)
    }
}
```

### Example Usage

```kotlin
import com.whoppah.common.permissions.PermissionGuard
import dev.icerock.moko.permissions.Permission

@Composable
fun CameraScreen() {
    PermissionGuard(
        permission = Permission.CAMERA,
        onGranted = {
            CameraPreview()
        },
        onDenied = {
            Text("Camera permission is required.")
        }
    )
}
```
