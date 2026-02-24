plugins {
    id("ua.isida.kotlin.multiplatform")
    alias(libs.plugins.kotlinx.serialization)
    id("ua.isida.metro")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.preferences)
            implementation(projects.core.logging)

            implementation(projects.data.models)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}