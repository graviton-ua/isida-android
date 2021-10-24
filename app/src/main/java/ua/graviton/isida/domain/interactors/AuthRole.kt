package ua.graviton.isida.domain.interactors

import ua.graviton.isida.data.exceptions.UnAuthException
import ua.graviton.isida.data.repos.RolesRepository
import ua.graviton.isida.domain.ResultInteractor
import ua.graviton.isida.domain.models.RoleModel
import ua.graviton.isida.domain.models.toDomain
import javax.inject.Inject

class AuthRole @Inject constructor(
    private val repo: RolesRepository,
) : ResultInteractor<AuthRole.Params, RoleModel>() {

    override suspend fun doWork(params: Params): RoleModel {
        val role = repo.authRole(params.login, params.pass)
        return role?.toDomain() ?: throw UnAuthException("User not authorized")
    }

    data class Params(val login: String, val pass: String)
}