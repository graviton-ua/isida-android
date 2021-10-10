package ua.graviton.isida.domain.interactors

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.graviton.isida.data.repos.RolesRepository
import ua.graviton.isida.domain.Interactor
import javax.inject.Inject

class AddRole @Inject constructor(
    private val repo: RolesRepository
) : Interactor<AddRole.Params>() {

    override suspend fun doWork(params: Params) = withContext(dispatcher) {
        repo.addRole(params.name, params.pass)
        // Ignore any result here
        Unit
    }

    data class Params(val name: String, val pass: String)

    companion object {
        private val dispatcher = Dispatchers.Default
    }
}