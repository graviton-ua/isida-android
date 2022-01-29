package ua.graviton.isida.data.repos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import ua.graviton.isida.data.bl.model.DataPackageDto
import ua.graviton.isida.data.db.AppDatabase
import ua.graviton.isida.data.db.entities.toEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceDataRepository @Inject constructor(
    private val db: AppDatabase
) {
    private val _latestData = MutableStateFlow<DataPackageDto?>(DataPackageDto.TestData)

    fun listenLatestRAMData() = _latestData.asStateFlow()

    suspend fun saveDataPackage(data: DataPackageDto) = withContext(dispatcher) {
        _latestData.value = data
        Result.runCatching {
            val entity = data.toEntity()
            db.deviceDataDao().insert(entity)
        }
    }

    fun saveDataEnd() {
        _latestData.value = null
    }

    companion object {
        private val dispatcher = Dispatchers.IO
    }
}