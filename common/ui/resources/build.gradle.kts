plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.android.library")
    id("com.whoppah.compose")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    android {
        namespace = "com.whoppah.common.resources"

        androidResources { enable = true }
        // defaultConfig {
        //     consumerProguardFiles("consumer-rules.pro")
        // }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.datetime)
            implementation(libs.jetbrains.compose.runtime)
            implementation(libs.jetbrains.compose.ui)
            api(libs.jetbrains.compose.resources)

            implementation(libs.kotlinx.serialization.json)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.whoppah.common.resources"
}