package ua.graviton.isida.data.db

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import ua.graviton.isida.data.sql.Database

expect interface SqlDatabasePlatformComponent

@ContributesTo(AppScope::class)
interface SqlDatabaseComponent : SqlDatabasePlatformComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideSqlDelightDatabase(factory: DatabaseFactory): Database = factory.build()
}
