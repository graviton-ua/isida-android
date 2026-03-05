plugins {
    id("ua.isida.kotlin.multiplatform")
    id("ua.isida.android.library")
    id("ua.isida.compose")
    id("ua.isida.metro")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {

    android {
        namespace = "ua.isida.ui.home.prop"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.logging)
            implementation(projects.core.preferences)
            implementation(projects.domain)

            implementation(projects.common.ui.compose)
            implementation(projects.common.ui.resources)
            implementation(projects.common.ui.metrox.viewmodel)
            implementation(projects.common.ui.navigation)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}