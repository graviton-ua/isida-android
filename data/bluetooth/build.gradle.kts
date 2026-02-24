plugins {
    id("ua.isida.kotlin.multiplatform")
    id("ua.isida.android.library")
    id("ua.isida.metro")
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