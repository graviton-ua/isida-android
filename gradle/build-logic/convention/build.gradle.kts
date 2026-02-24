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
            id = "ua.isida.kotlin.multiplatform"
            implementationClass = "ua.isida.gradle.KotlinMultiplatformConventionPlugin"
        }

        register("root") {
            id = "ua.isida.root"
            implementationClass = "ua.isida.gradle.RootConventionPlugin"
        }

        register("androidApplication") {
            id = "ua.isida.android.application"
            implementationClass = "ua.isida.gradle.AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "ua.isida.android.library"
            implementationClass = "ua.isida.gradle.AndroidLibraryConventionPlugin"
        }

        register("compose") {
            id = "ua.isida.compose"
            implementationClass = "ua.isida.gradle.ComposeMultiplatformConventionPlugin"
        }

        register("metro") {
            id = "ua.isida.metro"
            implementationClass = "ua.isida.gradle.MetroConventionPlugin"
        }
    }
}
