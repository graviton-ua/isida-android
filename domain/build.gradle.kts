plugins {
    id("ua.isida.kotlin.multiplatform")
    id("ua.isida.android.library")
    alias(libs.plugins.kotlinx.serialization)
    id("ua.isida.metro")
}

kotlin {

    android {
        namespace = "ua.isida.domain"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.logging)
            implementation(projects.core.preferences)

            api(projects.data.models)
            api(projects.data.repos)
            api(projects.data.bluetooth)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
        }
    }
}
