package ua.graviton.isida.domain.interactors

import dev.zacsweers.metro.Inject
import ua.graviton.isida.data.models.DataPackageDto
import ua.graviton.isida.data.repos.DeviceDataRepository
import ua.graviton.isida.domain.ResultInteractor

@Inject
class SaveDataPackage(
    private val repo: DeviceDataRepository
) : ResultInteractor<SaveDataPackage.Params, Unit>() {

    override suspend fun doWork(params: Params) {
        if (params.bytes == null) {
            repo.saveDataEnd()
            return
        }
        val dto = try {
            DataPackageDto.parseData(params.bytes)
        } catch (t: Throwable) {
            null
        } ?: return

        repo.saveDataPackage(dto).getOrThrow()  // Ignore result
    }

    @Suppress("ArrayInDataClass")
    data class Params(val bytes: ByteArray?)
}