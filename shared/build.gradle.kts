plugins {
    id("app.s2c.kotlin.multiplatform")
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

            api(projects.ui.home)
            api(projects.ui.configs)
            api(projects.ui.settings)
            api(projects.ui.services)
            api(projects.ui.balance)
            api(projects.ui.orders)
            api(projects.ui.blockchain)
        }
    }
}