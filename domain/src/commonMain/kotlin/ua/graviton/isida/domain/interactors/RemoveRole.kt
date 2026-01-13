package ua.graviton.isida.domain.interactors

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.graviton.isida.data.repos.RolesRepository
import ua.graviton.isida.domain.Interactor

@Inject
class RemoveRole(
    private val repo: RolesRepository
) : Interactor<RemoveRole.Params, Unit>() {

    override suspend fun doWork(params: Params) = withContext(dispatcher) {
        //TODO: val result = repo.removeRole(params.roleId)
        //if (!result) throw IllegalArgumentException("Role with id: ${params.roleId} wasn't deleted")
    }

    data class Params(val roleId: Long)

    companion object {
        private val dispatcher = Dispatchers.Default
    }
}