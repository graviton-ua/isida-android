import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

buildscript {
    dependencies {
        classpath(libs.classpath.javapoet)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.kapt) apply false
}

allprojects {

    // Configure Java to use our chosen language level. Kotlin will automatically pick this up
    plugins.withType<JavaBasePlugin>().configureEach {
        extensions.configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(17))
            }
        }
    }

    tasks.withType<KotlinCompilationTask<*>>().configureEach {
        compilerOptions {
            // Treat all Kotlin warnings as errors
            //allWarningsAsErrors.set(true)

            // Enable experimental coroutines APIs, including Flow
            //freeCompilerArgs.addAll(
            //    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            //    "-opt-in=kotlinx.coroutines.FlowPreview",
            //)

            if (project.hasProperty("myapp.enableComposeCompilerReports")) {
                freeCompilerArgs.addAll(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                            project.buildDir.absolutePath + "/compose_metrics",
                )
                freeCompilerArgs.addAll(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                            project.buildDir.absolutePath + "/compose_metrics",
                )
            }
        }
    }

    // Configure kapt
    pluginManager.withPlugin(rootProject.libs.plugins.kotlin.kapt.get().pluginId) {
        extensions.getByType<KaptExtension>().correctErrorTypes = true
    }

    // Configure Android projects
    pluginManager.withPlugin("com.android.application") { configureAndroidProject() }
    pluginManager.withPlugin("com.android.library") { configureAndroidProject() }
    pluginManager.withPlugin("com.android.test") { configureAndroidProject() }
}


// Remove also build folder in root folder
tasks.register<Delete>("clean") {
    delete.add(rootProject.buildDir)
}

fun Project.configureAndroidProject() {
    extensions.configure<BaseExtension> {
        compileSdkVersion(libs.versions.compileSdk.get().toInt())

        defaultConfig {
            minSdk = libs.versions.minSdk.get().toInt()
            targetSdk = libs.versions.targetSdk.get().toInt()
        }

        // Can remove this once https://issuetracker.google.com/issues/260059413 is fixed.
        // See https://kotlinlang.org/docs/gradle-configure-project.html#gradle-java-toolchains-support
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17

            // https://developer.android.com/studio/write/java8-support
            isCoreLibraryDesugaringEnabled = true
        }
    }

    dependencies {
        // https://developer.android.com/studio/write/java8-support
        "coreLibraryDesugaring"(libs.coreDesugaring)
    }
}


//buildscript {
//    ext {
//        minSdk = 23
//        targetSdk = 34
//        compileSdk = 34
//
//        kotlin_version = '1.7.21'
//        coreDesugaring = '2.0.0'
//
//        androidx = [
//            "core"      : '1.9.0',
//        "activity"  : '1.6.1',
//        "appcompat" : '1.6.0-rc01',
//        "room"      : '2.5.0-rc01',
//        "preference": '1.2.0',
//        "lifecycle" : '2.6.0-alpha03',
//        "navigation": '2.6.0-alpha04',
//        "compose"   : '1.3.1',
//        "annotation": '1.5.0',
//        ]
//        google = [
//            "hilt"       : '2.44.2',
//        "accompanist": '0.28.0',
//        ]
//        kotlinx = [
//            "coroutines"   : '1.6.4',
//        "serialization": '1.4.1',
//        ]
//        versions = [
//            "timber"              : '5.0.1',
//        "compose_destinations": '1.7.27-beta',
//        ]
//    }
//    repositories {
//        google()
//        mavenCentral()
//    }
//    dependencies {
//        classpath 'com.android.tools.build:gradle:8.0.2'
//        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
//        classpath "com.google.dagger:hilt-android-gradle-plugin:${google.hilt}"
//    }
//}