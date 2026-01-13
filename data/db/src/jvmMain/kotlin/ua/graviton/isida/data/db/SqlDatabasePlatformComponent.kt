package ua.graviton.isida.data.db

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import ua.graviton.isida.data.sql.Database
import java.io.File
import java.util.Properties

/**
 * Interface for providing configurations and data sources specific to an SQL Server database platform.
 */
actual interface SqlDatabasePlatformComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideSqlDriver(): SqlDriver {
        val dbFile = getDatabaseFile()
        val dbPath = dbFile.absolutePath

        // Configuration Properties
        val properties = Properties().apply { put("foreign_keys", "true") }

        val driver = JdbcSqliteDriver(
            url = "jdbc:sqlite:$dbPath",
            properties = properties
        )

        // Schema Creation Logic
        // Unlike Android, JDBC driver doesn't automatically call onCreate.
        // We check if the file existed before we created the driver connection (or if it's size 0).
        // Note: JdbcSqliteDriver creates the empty file immediately upon instantiation.
        try {
            // Check if tables exist to determine if we need to create schema
            // A simple check is to query sqlite_master
            val currentVersion = driver.executeQuery(
                identifier = null,
                sql = "PRAGMA user_version;",
                mapper = { cursor ->
                    // We must verify there is a row (cursor.next())
                    if (cursor.next().value) {
                        QueryResult.Value(cursor.getLong(0))
                    } else {
                        QueryResult.Value(0L)
                    }
                },
                parameters = 0,
                binders = null
            ).value

            // If version is null or 0, create the schema
            if (currentVersion == null || currentVersion == 0L) {
                Database.Schema.create(driver)
                driver.execute(null, "PRAGMA user_version = 1;", 0)
            }

            // Enable WAL
            driver.execute(null, "PRAGMA journal_mode=WAL;", 0)

        } catch (e: Exception) {
            // Handle migration or creation errors
            e.printStackTrace()
        }

        return driver
    }
}

private fun getDatabaseFile(): File {
    val appDir = getAppDir()
    if (!appDir.exists()) {
        appDir.mkdirs()
    }
    return File(appDir, "isida.db")
}

private fun getAppDir(): File {
    val os = System.getProperty("os.name").lowercase()
    return when {
        os.contains("win") -> {
            File(System.getenv("AppData"), "Isida/db")
        }

        os.contains("nix") || os.contains("nux") || os.contains("aix") -> {
            File(System.getProperty("user.home"), ".isida")
        }

        os.contains("mac") -> {
            File(System.getProperty("user.home"), "Library/Application Support/Isida")
        }

        else -> error("Unsupported operating system")
    }
}
