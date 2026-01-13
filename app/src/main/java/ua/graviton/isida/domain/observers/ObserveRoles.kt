package ua.graviton.isida.domain.observers

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.graviton.isida.data.repos.RolesRepository
import ua.graviton.isida.domain.SubjectInteractor
import ua.graviton.isida.domain.models.RoleModel
import ua.graviton.isida.domain.models.toDomain

@Inject
class ObserveRoles(
    private val repo: RolesRepository
) : SubjectInteractor<Unit, List<RoleModel>>() {

    init {
        invoke(Unit)
    }

    override suspend fun createObservable(params: Unit): Flow<List<RoleModel>> {
        return repo.listenRolesWithOperations()
            .map { list -> list.map { it.toDomain() } }
    }
}