plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.android.library")
    id("com.whoppah.compose")
    id("com.whoppah.metro")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {

    android {
        namespace = "ua.isida.ui.scan"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.logging)
            implementation(projects.core.preferences)

            implementation(projects.data.bluetooth)
            implementation(projects.domain)

            implementation(projects.common.ui.compose)
            implementation(projects.common.ui.resources)
            implementation(projects.common.ui.metrox.viewmodel)
            implementation(projects.common.ui.navigation)
            implementation(projects.common.ui.permissions)
            implementation(projects.common.ui.services)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}