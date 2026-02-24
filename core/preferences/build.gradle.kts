plugins {
    id("ua.isida.kotlin.multiplatform")
    id("ua.isida.android.library")
    id("ua.isida.metro")
}

kotlin {
    android {
        namespace = "ua.isida.core.preferences"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.base)
                implementation(libs.multiplatformsettings.core)
                implementation(libs.multiplatformsettings.coroutines)
            }
        }
    }
}