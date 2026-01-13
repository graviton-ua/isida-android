package ua.graviton.isida.data.repos

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.graviton.isida.data.db.dao.RolesDao

@Inject
@SingleIn(AppScope::class)
class RolesRepository(
    private val dao: RolesDao,
) {

    suspend fun getRole(id: Long) = withContext(dispatcher) { dao.getRole(id) }

    suspend fun getRoles() = withContext(dispatcher) { dao.getRoles() }
    fun listenRoles() = dao.listenRoles()

    suspend fun getRolesWithOperations() = withContext(dispatcher) { dao.getRolesWithOperations() }
    fun listenRolesWithOperations() = dao.listenRolesWithOperations()


    suspend fun authRole(name: String, pass: String) = withContext(dispatcher) { dao.getRoleWithOperations(name, pass) }


    // suspend fun enableOperationForRole(roleId: Long, operation: String) = withContext(dispatcher) {
    //     val newRoleOperation = OperationEntity(name = operation, roleId = roleId)
    //     dao.insert(newRoleOperation)
    // }
    //
    // suspend fun disableOperationForRole(roleId: Long, operation: String) = withContext(dispatcher) {
    //     dao.delete(roleId, operation)
    // }


    // suspend fun addRole(
    //     name: String,
    //     pass: String
    // ) = withContext(dispatcher) {
    //     // TODO: instead of raw pass we have to store pass hash, but for test we gonna use direct password
    //     val role = RoleEntity(name = name, passHash = pass)
    //     val id = dao.insert(role)
    //     role.copy(id = id)
    // }
    //
    // suspend fun removeRole(
    //     roleId: Long,
    // ) = withContext(dispatcher) {
    //     dao.delete(roleId) > 0
    // }


    companion object {
        private val dispatcher = Dispatchers.IO
    }
}