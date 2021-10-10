package ua.graviton.isida.domain.interactors

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.graviton.isida.data.repos.RolesRepository
import ua.graviton.isida.domain.Interactor
import javax.inject.Inject

class RemoveRole @Inject constructor(
    private val repo: RolesRepository
) : Interactor<RemoveRole.Params>() {

    override suspend fun doWork(params: Params) = withContext(dispatcher) {
        val result = repo.removeRole(params.roleId)
        if (!result) throw IllegalArgumentException("Role with id: ${params.roleId} wasn't deleted")
    }

    data class Params(val roleId: Long)

    companion object {
        private val dispatcher = Dispatchers.Default
    }
}