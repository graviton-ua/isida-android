plugins {
    id("ua.isida.kotlin.multiplatform")
    id("ua.isida.android.library")
    id("ua.isida.compose")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    android {
        namespace = "ua.isida.common.ui.resources"

        androidResources { enable = true }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.jetbrains.compose.runtime)
            implementation(libs.jetbrains.compose.ui)
            api(libs.jetbrains.compose.resources)

            implementation(libs.kotlinx.serialization.json)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "ua.isida.common.ui.resources"
}