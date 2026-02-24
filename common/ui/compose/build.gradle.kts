plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.datetime)
            implementation(projects.core.logging)
            api(libs.jetbrains.compose.icons)
            api(projects.common.ui.resources)

            implementation(libs.jetbrains.compose.runtime)
            api(libs.jetbrains.compose.ui)
            api(libs.jetbrains.compose.ui.tooling.preview)
            api(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.lifecycle.runtime)

            implementation(libs.kotlinx.serialization.json)
        }
    }
}