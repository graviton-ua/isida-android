package ua.graviton.isida.data.db

import app.cash.sqldelight.adapter.primitive.FloatColumnAdapter
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import dev.zacsweers.metro.Inject
import ua.graviton.isida.data.db.adapters.InstantColumnAdapter
import ua.graviton.isida.data.sql.Database
import ua.graviton.isida.data.sql.Device_data
import javax.sql.DataSource

/**
 * A factory class responsible for creating and configuring instances of [Database] using the provided [DataSource].
 *
 * @constructor
 * Creates an instance of [DatabaseFactory] with the specified [DataSource].
 *
 * @param dataSource The [DataSource] used to establish connections to the database.
 *
 * @inject
 * This class is intended to be used with a dependency injection framework, as indicated by the `@Inject` annotation.
 */
@Inject
class DatabaseFactory(
    private val driver: SqlDriver,
) {
    fun build(): Database = Database(
        driver = driver,
        device_dataAdapter = Device_data.Adapter(
            created_atAdapter = InstantColumnAdapter,
            device_idAdapter = IntColumnAdapter,
            pv_t0Adapter = FloatColumnAdapter,
            pv_t1Adapter = FloatColumnAdapter,
            pv_t2Adapter = FloatColumnAdapter,
            pv_rhAdapter = IntColumnAdapter,
            pv_co2Adapter = IntColumnAdapter,
            pv_timerAdapter = IntColumnAdapter,
            pv_flapAdapter = IntColumnAdapter,
            powerAdapter = IntColumnAdapter,
            fusesAdapter = IntColumnAdapter,
            errorsAdapter = IntColumnAdapter,
            warningAdapter = IntColumnAdapter,
        ),
    )
}
