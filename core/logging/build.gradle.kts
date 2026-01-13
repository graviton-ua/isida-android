plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.android.library")
    id("com.whoppah.metro")
}

kotlin {
    android {
        namespace = "com.whoppah.core.logging"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.base)
                api(libs.kermit.kermit)
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

        // iosMain {
        //     dependsOn(mobileMain)
        // }
    }
}