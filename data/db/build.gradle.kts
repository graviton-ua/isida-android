plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.android.library")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqldelight)
    id("com.whoppah.metro")
}

kotlin {
    android {
        namespace = "ua.graviton.isida.data.db"
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

            implementation(libs.sqldelight.coroutines)
            implementation(libs.sqldelight.primitive)
        }

        jvmMain.dependencies {
            implementation(libs.sqldelight.jvm.driver)
        }

        androidMain.dependencies {
            implementation(libs.sqldelight.android.driver)
        }
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("ua.graviton.isida.data.sql")
            dialect(libs.sqldelight.dialect)
        }
    }
}
