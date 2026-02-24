plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.compose")
    id("com.whoppah.metro")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
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

            implementation(projects.ui.properties)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}