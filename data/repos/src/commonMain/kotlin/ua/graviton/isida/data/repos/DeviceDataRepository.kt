package ua.graviton.isida.data.repos

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import ua.graviton.isida.data.db.dao.DeviceDataDao
import ua.graviton.isida.data.db.models.DeviceDataEntity
import ua.graviton.isida.data.models.DataPackageDto

@Inject
@SingleIn(AppScope::class)
class DeviceDataRepository(
    private val dao: DeviceDataDao,
) {
    private val _latestData = MutableStateFlow<DataPackageDto?>(null)

    fun listenLatestRAMData() = _latestData.asStateFlow()

    suspend fun saveDataPackage(data: DataPackageDto): Result<Long> = withContext(dispatcher) {
        _latestData.value = data
        Result.runCatching {
            val entity: DeviceDataEntity = data.toEntity()
            dao.insert(entity)
        }
    }

    fun saveDataEnd() {
        _latestData.value = null
    }

    companion object {
        private val dispatcher = Dispatchers.IO
    }
}

private fun DataPackageDto.toEntity(): DeviceDataEntity {
    return DeviceDataEntity(
        deviceId = cellId,
        pvT0 = pvT0, pvT1 = pvT1,
        pvT2 = pvT2, pvT3 = pvT3,
        pvRh = pvRh,
        pvCO2_1 = pvCO2_1, pvCO2_2 = pvCO2_2,
        pvCO2_3 = pvCO2_3,
        pvTimer = pvTimer,
        pvTmrCount = pvTmrCount,
        pvFlap = pvFlap,
        power = power, fuses = fuses,
        errors = errors, warning = warning,
        cost0 = cost0, cost1 = cost1,
        date = date, hours = hours,
    )
}