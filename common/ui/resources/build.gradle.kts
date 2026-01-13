plugins {
    id("com.whoppah.kotlin.multiplatform")
    id("com.whoppah.android.library")
    id("com.whoppah.compose")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    android {
        namespace = "com.whoppah.common.resources"

        androidResources { enable = true }
        // defaultConfig {
        //     consumerProguardFiles("consumer-rules.pro")
        // }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.datetime)
            implementation(libs.jetbrains.compose.runtime)
            implementation(libs.jetbrains.compose.ui)
            api(libs.jetbrains.compose.resources)

            implementation(libs.kotlinx.serialization.json)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.whoppah.common.resources"
}

// Task 1: Download strings from Lokalise (previously your 'updateLoco' task)
val downloadLocoStrings = tasks.register<Exec>("downloadLocoStrings") {
    group = "Lokalise"
    description = "Downloads string resources from the Lokalise service."

    // Define gradle properties as inputs for better caching and error checking.
    val lokaliseProject = providers.gradleProperty("LOKALISE_PROJECT").orNull
        ?: throw IllegalStateException("Missing LOKALISE_PROJECT in local.properties")
    val lokalisePersonalToken = providers.gradleProperty("LOKALISE_PERSONAL_TOKEN").orNull
        ?: throw IllegalStateException("Missing LOKALISE_PERSONAL_TOKEN in local.properties")

    val downloadPath = project.projectDir.resolve("src/commonMain/composeResources")

    // Declare the output directory for Gradle's up-to-date checks.
    outputs.dir(downloadPath)

    // We should always consider this task as not up-to-date
    outputs.upToDateWhen { false }

    commandLine(
        "lokalise2", "--project-id=$lokaliseProject", "--token=$lokalisePersonalToken",
        "file", "download", "--format=xml", "--unzip-to=$downloadPath", "--indentation=4sp", "--export-empty-as=base",
        "--filter-langs=en,nl_NL,de,fr,it,es", "--exclude-tags=color,material,style,category",
        "--async"
    )

    doFirst {
        println("Starting download from Lokalise...")
    }
    doLast {
        println("Download complete.")
    }
}

// Task 2: Process the downloaded files to replace placeholders.
val processLocoStrings = tasks.register("processLocoStrings") {
    group = "Lokalise"
    description = "Adapts Android platform-specific string formatting for use in common Compose Multiplatform resources."

    // This task will run automatically after the download task finishes.
    dependsOn(downloadLocoStrings)

    doLast {
        val resourcesDir = project.projectDir.resolve("src/commonMain/composeResources")
        println("Processing downloaded files in: $resourcesDir")

        if (!resourcesDir.exists()) {
            println("Resource directory not found. Skipping processing.")
            return@doLast
        }

        // Find all XML files within the downloaded resource directories.
        project.fileTree(resourcesDir) {
            include("**/*.xml")
        }.forEach { file ->
            println("-> Processing ${file.name}")

            var content = file.readText()
            val originalContent = content

            // Perform the required replacements.
            content = content.replace("%%", "%")
            content = content.replace("%.0f", "%1\$d")
            content = content.replace("\\'", "'")

            // Only write back to the file if changes were made.
            if (content != originalContent) {
                file.writeText(content)
                println("   File updated.")
            } else {
                println("   No changes needed.")
            }
        }
        println("File processing complete.")
    }
}

// Main task: This will be the single task you run from the command line.
// It ensures that both downloading and processing happen in the correct order.
tasks.register("updateLoco") {
    group = "Lokalise"
    description = "Downloads and processes string resources from Lokalise."
    dependsOn(processLocoStrings)
    doLast {
        println("Lokalise strings updated and processed successfully!")
    }
}