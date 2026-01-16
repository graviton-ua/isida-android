plugins {
    id("com.whoppah.kotlin.multiplatform")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.core)
        }
    }
}