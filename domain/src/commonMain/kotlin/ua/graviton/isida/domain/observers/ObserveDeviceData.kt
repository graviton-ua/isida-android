package ua.graviton.isida.domain.observers

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.graviton.isida.data.models.DataPackageDto
import ua.graviton.isida.domain.SubjectInteractor
import ua.graviton.isida.domain.bluetooth.DeviceConnectionManager

@Inject
class ObserveDeviceData(
    private val repo: DeviceConnectionManager,
) : SubjectInteractor<Unit, DataPackageDto?>() {

    init {
        invoke(Unit)
    }

    override suspend fun createObservable(params: Unit): Flow<DataPackageDto?> {
        return repo.dataStream.map { data ->
            try {
                DataPackageDto.parseData(data)
            } catch (_: Exception) {
                null
            }
        }
    }
}