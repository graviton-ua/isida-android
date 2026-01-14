package ua.graviton.isida.data.db

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import ua.graviton.isida.data.sql.Database

/**
 * Interface for providing configurations and data sources specific to an SQL Server database platform.
 */
actual interface SqlDatabasePlatformComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideSqlDriver(
        @Named("APPLICATION_CONTEXT") context: Context,
    ): SqlDriver {
        return AndroidSqliteDriver(
            schema = Database.Schema,
            context = context,
            name = "isida.db", // Name of the file on disk
            callback = object : AndroidSqliteDriver.Callback(schema = Database.Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    // Enable Foreign Keys
                    db.setForeignKeyConstraintsEnabled(true)
                    // Enable WAL for concurrency (Read while Writing)
                    db.enableWriteAheadLogging()
                }
            }
        )
    }
}