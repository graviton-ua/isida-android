plugins {
    id("ua.isida.kotlin.multiplatform")
    id("ua.isida.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.common.ui.resources)

            api(libs.jetbrains.compose.runtime)
            api(libs.jetbrains.navigation3.ui)
            implementation(libs.jetbrains.lifecycle.viewmodel.navigation3)
        }
    }
}
