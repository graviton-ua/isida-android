plugins {
    id("ua.isida.root")

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.kotlin.compose.compiler) apply false
    alias(libs.plugins.metro) apply false
    alias(libs.plugins.sqldelight) apply false
}

subprojects {
    // This can be removed as soon as we migrate all modules to KMP, as our KotlinMultiplatformConvention plugin contains this opt-in
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            optIn.add("kotlin.time.ExperimentalTime")
            freeCompilerArgs.add("-Xskip-prerelease-check")
        }
    }
}

// Remove also build folder in root folder
tasks.register<Delete>("clean") {
    delete.add(rootProject.layout.buildDirectory)
}