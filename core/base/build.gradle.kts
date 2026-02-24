plugins {
    id("ua.isida.kotlin.multiplatform")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.atomicfu)
            api(libs.kotlinx.coroutines.core)
        }
    }
}