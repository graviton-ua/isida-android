# ISIDA Project

![Kotlin](https://img.shields.io/badge/Kotlin-2.3.20--RC--73-blue.svg?logo=kotlin)
![Compose Multiplatform](https://img.shields.io/badge/Compose_Multiplatform-1.10.0-blue.svg?logo=jetbrains)
![Android Gradle Plugin](https://img.shields.io/badge/AGP-9.0.0-green.svg?logo=android)
![Platform](https://img.shields.io/badge/Platform-Android_%7C_Desktop_(JVM)-lightgrey.svg)

**ISIDA** is a Kotlin Multiplatform (KMP) project targeting Android and Desktop environments. It leverages Jetpack Compose for a unified UI experience across platforms and integrates hardware interaction features like Bluetooth communication.

## 🚀 Features

*   **Multiplatform UI**: Built with Compose Multiplatform for consistent design on Android and Desktop.
*   **Bluetooth Connectivity**: robust Bluetooth integration (`:data:bluetooth`) for device communication.
*   **Device Management**: Features for device scanning (`:ui:scan`), property setting (`:ui:setprop`), and device modes (`:ui:devicemode`).
*   **Data Visualization**: Home screen with reporting (`:ui:home:report`) and statistics (`:ui:home:stats`).
*   **Modular Architecture**: Clean separation of concerns with dedicated Core, Data, Domain, and UI layers.

## 🛠 Tech Stack

*   **Language**: [Kotlin](https://kotlinlang.org/) (Multiplatform)
*   **UI Framework**: [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) & Material 3
*   **Asynchrony**: Kotlin Coroutines & Flow
*   **Database**: [SQLDelight](https://cashapp.github.io/sqldelight/)
*   **Dependency Injection**: Metro
*   **Navigation**: Jetbrains Navigation 3
*   **Logging**: Kermit
*   **Permissions**: Moko Permissions
*   **Settings**: Multiplatform Settings
*   **Serialization**: Kotlinx Serialization

## 📂 Project Structure

The project follows a modular architecture:

*   **`androidApp`**: Android application entry point.
*   **`desktopApp`**: Desktop (JVM) application entry point.
*   **`common`**: Shared UI components, resources, navigation, and services.
    *   `ui/compose`: Common Compose UI elements.
    *   `ui/resources`: Shared strings, images, and fonts.
*   **`core`**: Foundational utilities.
    *   `base`, `datetime`, `logging`, `preferences`.
*   **`data`**: Data handling layer.
    *   `bluetooth`: Bluetooth communication logic.
    *   `models`: Shared data models.
    *   `repos`: Repositories and data access patterns.
*   **`domain`**: Business logic and use cases.
*   **`ui`**: Feature-specific UI modules.
    *   `home`, `scan`, `setprop`, `devicemode`.

## 🏁 Getting Started

### Prerequisites

*   **JDK 17** or higher.
*   **Android Studio** (Ladybug or newer recommended for KMP support).
*   **IntelliJ IDEA** (Optional, for Desktop development).

### Building the Project

Open the project directory in your terminal:

```bash
# Build the project
./gradlew build
```

### Running the Application

#### Android

Connect a device or start an emulator:

```bash
./gradlew :androidApp:installDebug
```

#### Desktop

Run the desktop application directly from Gradle:

```bash
./gradlew :desktopApp:run
```

## 📝 Configuration

Key project versions are managed in `gradle/libs.versions.toml`.

*   **Kotlin**: `2.3.20-RC-73`
*   **Compose**: `1.10.0`
*   **AGP**: `9.0.0`
