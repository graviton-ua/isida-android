plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.composeCompiler.gradlePlugin)
    compileOnly(libs.metro.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatform") {
            id = "com.whoppah.kotlin.multiplatform"
            implementationClass = "com.whoppah.gradle.KotlinMultiplatformConventionPlugin"
        }

        register("root") {
            id = "com.whoppah.root"
            implementationClass = "com.whoppah.gradle.RootConventionPlugin"
        }

        register("androidApplication") {
            id = "com.whoppah.android.application"
            implementationClass = "com.whoppah.gradle.AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "com.whoppah.android.library"
            implementationClass = "com.whoppah.gradle.AndroidLibraryConventionPlugin"
        }

        register("compose") {
            id = "com.whoppah.compose"
            implementationClass = "com.whoppah.gradle.ComposeMultiplatformConventionPlugin"
        }

        register("metro") {
            id = "com.whoppah.metro"
            implementationClass = "com.whoppah.gradle.MetroConventionPlugin"
        }
    }
}
