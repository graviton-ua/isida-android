package ua.isida.gradle

import dev.zacsweers.metro.gradle.DelicateMetroGradleApi
import dev.zacsweers.metro.gradle.ExperimentalMetroGradleApi
import dev.zacsweers.metro.gradle.MetroPluginExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class MetroConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("dev.zacsweers.metro")
            }

            configureMetro()
        }
    }
}

@OptIn(DelicateMetroGradleApi::class, ExperimentalMetroGradleApi::class)
private fun Project.configureMetro() {
    metro {
        generateContributionHintsInFir.set(true)
    }
}

private fun Project.metro(action: MetroPluginExtension.() -> Unit) = extensions.configure<MetroPluginExtension>(action)