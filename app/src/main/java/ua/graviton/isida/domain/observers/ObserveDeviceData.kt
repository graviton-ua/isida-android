package ua.graviton.isida.domain.observers

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import ua.graviton.isida.data.models.DataPackageDto
import ua.graviton.isida.data.repos.DeviceDataRepository
import ua.graviton.isida.domain.SubjectInteractor

@Inject
class ObserveDeviceData(
    private val repo: DeviceDataRepository
) : SubjectInteractor<Unit, DataPackageDto?>() {

    init {
        invoke(Unit)
    }

    override suspend fun createObservable(params: Unit): Flow<DataPackageDto?> {
        return repo.listenLatestRAMData()
    }
}