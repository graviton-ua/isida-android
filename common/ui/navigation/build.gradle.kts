plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.common.ui.resources)

            api(libs.jetbrains.compose.runtime)
            api(libs.jetbrains.navigation3.ui)
        }
    }
}
