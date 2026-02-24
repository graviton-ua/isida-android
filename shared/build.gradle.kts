plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.android.library")
    id("com.whoppah.compose")
    id("com.whoppah.metro")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {

    android {
        namespace = "ua.graviton.isida.shared"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core.base)
            api(projects.core.logging)
            api(projects.core.preferences)

            api(projects.common.ui.compose)
            api(projects.common.ui.metrox.viewmodel)
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

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.jetbrains.lifecycle.viewmodel.navigation3)
        }
    }
}