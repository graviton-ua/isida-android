plugins {
    id("ua.isida.kotlin.multiplatform")
    id("ua.isida.android.library")
    id("ua.isida.compose")
}

kotlin {
    android {
        namespace = "ua.isida.common.ui.services"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.logging)

            implementation(libs.jetbrains.compose.runtime)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.google.gms.location)
        }
    }
}