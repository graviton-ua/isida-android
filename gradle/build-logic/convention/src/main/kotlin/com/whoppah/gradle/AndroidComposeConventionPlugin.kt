package com.whoppah.gradle

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        //pluginManager.apply("org.jetbrains.compose")
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
        configureAndroidCompose()
        configureCompose()
    }
}

fun Project.configureAndroidCompose() {
    androidCompose {
        buildFeatures.compose = true
    }
}

//fun Project.configureCompose() {
//    composeCompiler {
//        // Needed for Layout Inspector to be able to see all of the nodes in the component tree:
//        //https://issuetracker.google.com/issues/338842143
//        includeSourceInformation.set(true)
//
//        if (project.providers.gradleProperty("tivi.enableComposeCompilerReports").isPresent) {
//            val composeReports = layout.buildDirectory.map { it.dir("reports").dir("compose") }
//            reportsDestination.set(composeReports)
//            metricsDestination.set(composeReports)
//        }
//
//        stabilityConfigurationFile.set(rootProject.file("compose-stability.conf"))
//    }
//
//    // Workaround for:
//    // Task 'generateDebugUnitTestLintModel' uses this output of task
//    // 'generateResourceAccessorsForAndroidUnitTest' without declaring an explicit or
//    // implicit dependency.
//    tasks.matching { it is AndroidLintAnalysisTask || it is LintModelWriterTask }.configureEach {
//        mustRunAfter(tasks.matching { it.name.startsWith("generateResourceAccessorsFor") })
//    }
//}

fun Project.androidCompose(block: CommonExtension.() -> Unit) {
    extensions.configure<CommonExtension>(block)
}

//fun Project.composeCompiler(block: ComposeCompilerGradlePluginExtension.() -> Unit) {
//    extensions.configure<ComposeCompilerGradlePluginExtension>(block)
//}
