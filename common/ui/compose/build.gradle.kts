plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.android.library")
    id("com.whoppah.compose")
}

kotlin {
    android {
        namespace = "com.whoppah.common.compose"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.datetime)
            implementation(projects.core.logging)
            api(projects.common.ui.composeIcons)
            api(projects.common.ui.resources)

            implementation(libs.jetbrains.compose.runtime)
            api(libs.jetbrains.compose.ui)
            api(libs.jetbrains.compose.ui.tooling.preview)
            api(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.lifecycle.runtime)
            implementation(libs.jetbrains.navigation.compose)

            implementation(libs.kotlinx.serialization.json)
        }
    }
}