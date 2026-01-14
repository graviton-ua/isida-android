package ua.graviton.isida.domain.interactors

import dev.zacsweers.metro.Inject
import ua.graviton.isida.data.repos.RolesRepository
import ua.graviton.isida.domain.ResultInteractor
import ua.graviton.isida.domain.exceptions.UnAuthException
import ua.graviton.isida.domain.models.RoleModel

@Inject
class AuthRole(
    private val repo: RolesRepository,
) : ResultInteractor<AuthRole.Params, RoleModel>() {

    override suspend fun doWork(params: Params): RoleModel {
        // TODO:  val role = repo.authRole(params.login, params.pass)
        // return role?.toDomain() ?: throw UnAuthException("User not authorized")
        throw UnAuthException("Not implemented")
    }

    data class Params(val login: String, val pass: String)
}