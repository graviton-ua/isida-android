plugins {
    id("com.whoppah.kotlin.multiplatform")
    alias(libs.plugins.metro)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.base)
            api(projects.core.logging)
            api(projects.core.preferences)
            api(projects.data.repos)
            api(projects.domain)

            api(projects.common.ui.metrox.viewmodel)

            api(projects.ui.devicemode)
            api(projects.ui.home.base)
            api(projects.ui.home.prop)
            api(projects.ui.home.report)
            api(projects.ui.home.stats)
            api(projects.ui.scan)
            api(projects.ui.setprop)
        }
    }
}