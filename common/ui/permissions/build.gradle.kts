plugins {
    id("ua.isida.kotlin.multiplatform")
    id("ua.isida.android.library")
    id("ua.isida.compose")
}

kotlin {
    android {
        namespace = "ua.isida.common.ui.permissions"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.logging)

            implementation(libs.jetbrains.compose.runtime)
            implementation(libs.jetbrains.lifecycle.runtime)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)

            implementation(libs.moko.permissions.bluetooth)
            implementation(libs.moko.permissions.location)
            implementation(libs.moko.permissions.notifications)
            implementation(libs.moko.permissions.storage)
            implementation(libs.moko.permissions.compose)
        }
    }
}