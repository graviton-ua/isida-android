plugins {
    id("com.whoppah.kotlin.multiplatform")
    alias(libs.plugins.kotlinx.serialization)
    id("com.whoppah.metro")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.preferences)
            implementation(projects.core.logging)

            api(projects.data.db)
            //api(projects.data.web)
            implementation(projects.data.models)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}