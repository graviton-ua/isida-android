plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.android.library")
    id("com.whoppah.metro")
}

kotlin {
    android {
        namespace = "ua.isida.data.bluetooth"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.logging)
        }

        jvmMain.dependencies {
            implementation(libs.jSerialComm)
        }
    }
}