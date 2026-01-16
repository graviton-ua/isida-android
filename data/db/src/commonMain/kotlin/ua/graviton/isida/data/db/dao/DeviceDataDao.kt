package ua.graviton.isida.data.db.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.whoppah.util.AppCoroutineDispatchers
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ua.graviton.isida.data.db.models.DeviceDataEntity
import ua.graviton.isida.data.sql.Database
import kotlin.time.Instant

@Inject
@SingleIn(AppScope::class)
class DeviceDataDao(
    dispatchers: AppCoroutineDispatchers,
    db: Database,
) : Dao(dispatcher = dispatchers.io, db = db) {

    suspend fun getLatest(): List<DeviceDataEntity> = transaction { deviceDataQueries.selectLatest(mapper = mapper).executeAsList() }

    fun observeLatest(): Flow<List<DeviceDataEntity>> = db.deviceDataQueries.selectLatest(mapper = mapper)
        .asFlow()
        .mapToList(dispatcher)

    suspend fun insert(dto: DeviceDataEntity) = withContext(dispatcher) {
        db.deviceDataQueries.insertDeviceData(
            created_at = dto.createdAt,
            device_id = dto.deviceId,
            pv_t0 = dto.pvT0,
            pv_t1 = dto.pvT1,
            pv_t2 = dto.pvT2,
            pv_rh = dto.pvRh,
            pv_co2 = dto.pvCO2,
            pv_timer = dto.pvTimer,
            pv_flap = dto.pvFlap,
            power = dto.power,
            fuses = dto.fuses,
            errors = dto.errors,
            warning = dto.warning,
        ).await()
    }
}

private val mapper: (
    id: Long,
    created_at: Instant,
    device_id: Int,
    pv_t0: Float,
    pv_t1: Float,
    pv_t2: Float,
    pv_rh: Int,
    pv_co2: Int,
    pv_timer: Int,
    pv_flap: Int,
    power: Int,
    fuses: Int,
    errors: Int,
    warning: Int,
) -> DeviceDataEntity = ::DeviceDataEntity