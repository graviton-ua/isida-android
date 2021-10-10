package ua.graviton.isida.domain.interactors

import ua.graviton.isida.data.bl.model.DataPackageDto
import ua.graviton.isida.data.repos.DeviceDataRepository
import ua.graviton.isida.domain.Interactor
import javax.inject.Inject

class SaveDataPackage @Inject constructor(
    private val repo: DeviceDataRepository
) : Interactor<SaveDataPackage.Params>() {

    override suspend fun doWork(params: Params) {
        val dto = DataPackageDto.parseData(params.bytes)
        repo.saveDataPackage(dto).getOrThrow()  // Ignore result
    }

    @Suppress("ArrayInDataClass")
    data class Params(val bytes: ByteArray)
}