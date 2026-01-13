rootProject.name = "isida"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
// https://docs.gradle.org/7.6/userguide/configuration_cache.html#config_cache:stable
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
    includeBuild("gradle/build-logic")

    repositories {
        google {
            content {
                includeGroupByRegex(".*google.*")
                includeGroupByRegex(".*android.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://plugins.gradle.org/m2/") }

        // Prerelease versions of Compose Multiplatform
        // maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://packages.jetbrains.team/maven/p/kt/dev/")

        // Used for snapshots if needed
        // maven("https://oss.sonatype.org/content/repositories/snapshots/")
        // maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}
dependencyResolutionManagement {
    repositories {
        google {
            content {
                includeGroupByRegex(".*google.*")
                includeGroupByRegex(".*android.*")
            }
        }
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.google.com/") }
        maven { url = uri("https://maven.lokalise.co") }
        maven { url = uri("https://appboy.github.io/appboy-android-sdk/sdk") }

        // Prerelease versions of Compose Multiplatform
        // maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://packages.jetbrains.team/maven/p/kt/dev/")

        // Used for snapshots if needed
        // maven("https://oss.sonatype.org/content/repositories/snapshots/")
        // maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

include(
    ":core:base",
    ":core:datetime",
    ":core:logging",
    ":core:preferences",

    ":common:ui:compose",
    ":common:ui:compose-icons",
    ":common:ui:metrox:viewmodel",
    ":common:ui:resources",
    ":common:ui:permissions",

    ":data:db",
    ":data:models",
    ":data:repos",

    ":domain",

    ":app",
)