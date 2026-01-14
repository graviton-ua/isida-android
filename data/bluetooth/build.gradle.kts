plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.android.library")
    id("com.whoppah.metro")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    android {
        namespace = "ua.graviton.isida.data.bluetooth"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.logging)

            implementation(projects.data.models)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            // Need to force upgrade these for recent Kotlin support
            // api(libs.kotlinx.atomicfu)
            implementation(libs.kotlinx.coroutines.core)
        }

        jvmMain.dependencies {
            //implementation(libs.sqldelight.jvm.driver)
        }

        androidMain.dependencies {
            //implementation(libs.sqldelight.android.driver)
        }
    }
}