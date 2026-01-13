package ua.graviton.isida.domain.interactors

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.graviton.isida.data.repos.RolesRepository
import ua.graviton.isida.domain.Interactor

@Inject
class AddRole(
    private val repo: RolesRepository
) : Interactor<AddRole.Params, Unit>() {

    override suspend fun doWork(params: Params) = withContext(dispatcher) {
        //TODO: repo.addRole(params.name, params.pass)
        // Ignore any result here
        Unit
    }

    data class Params(val name: String, val pass: String)

    companion object {
        private val dispatcher = Dispatchers.Default
    }
}