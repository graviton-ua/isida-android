plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.android.library")
    id("com.whoppah.compose")
}

kotlin {
    android {
        namespace = "com.whoppah.common.compose.icons"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.compose.icons)
            implementation(libs.jetbrains.compose.ui.tooling.preview)
        }

        androidMain.dependencies {
            implementation(libs.jetbrains.compose.ui.tooling)   // for Preview rendering in AndroidStudio
        }
    }
}