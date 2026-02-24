plugins {
    id("ua.isida.kotlin.multiplatform")
    id("ua.isida.compose")
    id("ua.isida.metro")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.logging)

            api(libs.jetbrains.lifecycle.runtime)
            api(libs.jetbrains.lifecycle.viewmodel)
        }
    }
}