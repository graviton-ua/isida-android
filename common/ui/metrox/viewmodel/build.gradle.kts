plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.compose")
    id("com.whoppah.metro")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.logging)

            api(libs.jetbrains.lifecycle.runtime)
            api(libs.jetbrains.lifecycle.viewmodel)
            api(libs.jetbrains.navigation.compose)
        }
    }
}