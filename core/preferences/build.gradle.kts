plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.android.library")
    id("com.whoppah.metro")
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