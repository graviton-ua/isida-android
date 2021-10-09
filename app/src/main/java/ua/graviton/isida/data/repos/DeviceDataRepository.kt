package ua.graviton.isida.data.repos

import kotlinx.coroutines.Dispatchers
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
    companion object {
        private val dispatcher = Dispatchers.IO
    }

    suspend fun saveDataPackage(data: DataPackageDto) = withContext(dispatcher) {
        Result.runCatching {
            val entity = data.toEntity()
            db.deviceDataDao().insert(entity)
        }
    }
}