plugins {
    id("ua.isida.kotlin.multiplatform")
    id("ua.isida.android.library")
    id("ua.isida.metro")
}

kotlin {
    android {
        namespace = "ua.isida.core.logging"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.base)
                api(libs.kermit.kermit)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.io.core)
            }
        }

        val mobileMain by creating {
            dependsOn(commonMain.get())

            dependencies {
                implementation(libs.crashkios.crashlytics)
            }
        }

        androidMain {
            dependsOn(mobileMain)

            dependencies {
                implementation(project.dependencies.platform(libs.firebase.bom))
                implementation(libs.firebase.crashlytics)
            }
        }
    }
}