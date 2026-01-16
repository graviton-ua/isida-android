plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.android.library")
    id("com.whoppah.metro")
    alias(libs.plugins.sqldelight)
}

kotlin {
    android {
        namespace = "ua.graviton.isida.data.db"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.logging)

            implementation(projects.data.models)
            implementation(libs.kotlinx.datetime)

            implementation(libs.sqldelight.coroutines)
            implementation(libs.sqldelight.primitive)
        }

        jvmMain.dependencies {
            implementation(libs.sqldelight.jvm.driver)
        }

        androidMain.dependencies {
            implementation(libs.sqldelight.android.driver)
        }
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("ua.graviton.isida.data.sql")
            dialect(libs.sqldelight.dialect)
        }
    }
}
