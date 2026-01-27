plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.android.library")
    alias(libs.plugins.kotlinx.serialization)
    id("com.whoppah.metro")
}

kotlin {
    android {
        namespace = "ua.graviton.isida.data.repos"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.preferences)
            implementation(projects.core.logging)

            implementation(projects.data.models)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}