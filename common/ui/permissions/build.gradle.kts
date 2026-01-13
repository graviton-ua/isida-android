plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.android.library")
    id("com.whoppah.compose")
}

kotlin {
    android {
        namespace = "com.whoppah.common.permissions"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.logging)

            implementation(libs.jetbrains.compose.runtime)
            implementation(libs.jetbrains.lifecycle.runtime)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)

            implementation(libs.moko.permissions.camera)
            implementation(libs.moko.permissions.notifications)
            implementation(libs.moko.permissions.storage)
            implementation(libs.moko.permissions.compose)
        }
        // iosMain.dependencies {
        //     implementation(libs.moko.permissions.camera)
        //     implementation(libs.moko.permissions.notifications)
        //     implementation(libs.moko.permissions.storage)
        //     implementation(libs.moko.permissions.compose)
        // }
    }
}