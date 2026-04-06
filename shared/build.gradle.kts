plugins {
    id("ua.isida.kotlin.multiplatform")
    id("ua.isida.android.library")
    id("ua.isida.compose")
    id("ua.isida.metro")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {

    android {
        namespace = "ua.isida.shared"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core.base)
            api(projects.core.logging)
            api(projects.core.preferences)

            api(projects.common.ui.compose)
            api(projects.common.ui.permissions)
            api(projects.common.ui.resources)
            api(projects.common.ui.navigation)
            
            api(projects.data.repos)
            api(projects.domain)

            api(projects.ui.devicemode)
            api(projects.ui.home.base)
            api(projects.ui.home.prop)
            api(projects.ui.home.program)
            api(projects.ui.home.stats)
            api(projects.ui.scan)
            api(projects.ui.setprop)
            api(projects.ui.setday)
            api(projects.ui.logreport)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.jetbrains.lifecycle.viewmodel.navigation3)
            api(libs.bundles.metrox.viewmodel)
        }
    }
}