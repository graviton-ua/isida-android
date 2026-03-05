plugins {
    id("ua.isida.kotlin.multiplatform")
    id("ua.isida.android.library")
    id("ua.isida.compose")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {

    android {
        namespace = "ua.isida.ui.properties"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.logging)
            implementation(projects.domain)

            implementation(projects.common.ui.compose)
            implementation(projects.common.ui.resources)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}