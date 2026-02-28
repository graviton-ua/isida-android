plugins {
    id("ua.isida.android.application")
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.google.gms)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.appdistribution)
    id("ua.isida.metro")
}

val githubVersionCode = System.getenv("GITHUB_RUN_NUMBER")?.toIntOrNull() ?: 0

android {
    namespace = "ua.isida"

    defaultConfig {
        applicationId = "ua.isida"

        versionCode = githubVersionCode + 1
        versionName = gitDescribe(project.providers).get()
        versionNameSuffix = versionSuffix(project.providers).get()

        // setProperty("archivesBaseName", "app-v$versionName")
        base.archivesName = "app-v$versionName"

        manifestPlaceholders += mapOf(
            "notification_channel_General" to "General",
        )

        buildConfigField("String", "NOTIFICATION_CHANNEL_ID_GENERAL", "\"${manifestPlaceholders["notification_channel_General"]}\"")
    }

    signingConfigs {
        named("debug") {
            storeFile = file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        create("release") {
            val keystoreFile = file("release.keystore")
            if (keystoreFile.exists()) {
                storeFile = keystoreFile
                storePassword = System.getenv("RELEASE_KEYSTORE_PASSWORD") ?: ""
                keyAlias = "isida"
                keyPassword = System.getenv("RELEASE_KEYSTORE_PASSWORD") ?: ""
            }
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            versionNameSuffix = "-DEBUG"
            signingConfig = signingConfigs.getByName("debug")

            buildConfigField("Boolean", "CRASH_REPORTING", "false")
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = if (project.file("release.keystore").exists()) signingConfigs.getByName("release") else signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.shared)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    debugImplementation(libs.leakcanary.android)
}



fun versionSuffix(providers: ProviderFactory): Provider<String> {
    return providers.exec {
        commandLine("git", "branch", "--show-current")
    }.standardOutput.asText.map { branch ->
        val branchName = branch.trim()
        when {
            branchName.matches("""release/(.+)""".toRegex()) -> "-RC"
            branchName.matches("""feature/(.+)""".toRegex()) -> "-FEATURE"
            else -> ""
        }
    }
}

fun gitDescribe(providers: ProviderFactory): Provider<String> {
    return providers.exec {
        commandLine("git", "describe", "--tags", "--always")
    }.standardOutput.asText.map { it.split("\n").first().trim() }
}

/**
 * Generates git log notes from the latest tag up to HEAD.
 * Handles the "release day" scenario (when HEAD is the same as the latest tag)
 * by using the range from the second latest tag to HEAD.
 * Handles cases with 0 or 1 tag by using a fallback commit count.
 * This is a cross-platform implementation using 'git' commands and Kotlin processing.
 *
 * @param providers Gradle's ProviderFactory service.
 * @param maxCountFallback The maximum number of commits to show if fewer than two tags exist
 * or if HEAD matches the only existing tag. Defaults to 20.
 * @return A Provider<String> containing the formatted git log.
 */
fun gitNotes(providers: ProviderFactory, maxCountFallback: Int = 20): Provider<String> {

    // Provider to get all tags, sorted by version descending (newest first)
    val sortedTagsProvider: Provider<List<String>> = providers.exec {
        commandLine("git", "tag", "--sort=-v:refname")
    }.standardOutput.asText.map { gitTagOutput ->
        // Process the raw output in Kotlin for cross-platform compatibility
        gitTagOutput.lines()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }

    // Chain operations based on the sorted tags result
    return sortedTagsProvider.flatMap { sortedTags ->
        val latestTag = sortedTags.getOrNull(0)
        val previousTag = sortedTags.getOrNull(1)

        if (latestTag == null) {
            // ---- Case 1: No tags exist ----
            providers.exec {
                val source = "--max-count=$maxCountFallback"
                commandLine("git", "log", "--pretty=* %s (%an) [%h]", source)
            }.standardOutput.asText.map { it.trim() }
        } else {
            // ---- Case 2: At least one tag exists ----
            // Need to compare HEAD commit hash with the latest tag's commit hash.
            val headHashProvider: Provider<String> = providers.exec {
                commandLine("git", "rev-parse", "HEAD")
            }.standardOutput.asText.map { it.trim() }

            val latestTagHashProvider: Provider<String> = providers.exec {
                commandLine("git", "rev-parse", latestTag)
            }.standardOutput.asText.map { it.trim() }

            // Combine hash providers using zip to make the decision
            headHashProvider.zip(latestTagHashProvider) { headHash, latestTagHash ->
                // Decide which tag name (String?) to use as the log starting point.
                if (headHash == latestTagHash) {
                    // HEAD is the same as the latest tag ("release day" scenario).
                    // Use the tag *before* the latest one.
                    previousTag
                } else {
                    // HEAD is ahead of the latest tag. Use the latest tag.
                    latestTag
                }
            }.flatMap { tag ->
                val source = if (tag.isNullOrEmpty()) "--max-count=$maxCountFallback" else "$tag..HEAD"

                providers.exec {
                    commandLine("git", "log", "--pretty=* %s (%an) [%h]", source)
                }.standardOutput.asText.map { it.trim() }
            }
        }
    }
}