package ua.graviton.isida.domain.observers

import kotlinx.coroutines.flow.Flow
import ua.graviton.isida.data.bl.model.DataPackageDto
import ua.graviton.isida.data.repos.DeviceDataRepository
import ua.graviton.isida.domain.SubjectInteractor
import javax.inject.Inject

class ObserveDeviceData @Inject constructor(
    private val repo: DeviceDataRepository
) : SubjectInteractor<Unit, DataPackageDto?>() {

    init {
        // Do it only carefully!!!
        invoke(Unit)
    }

    override suspend fun createObservable(params: Unit): Flow<DataPackageDto?> {
        return repo.listenLatestRAMData()
    }
}