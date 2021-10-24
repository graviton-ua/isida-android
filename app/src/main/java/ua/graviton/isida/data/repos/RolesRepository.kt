package ua.graviton.isida.data.repos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.graviton.isida.data.db.AppDatabase
import ua.graviton.isida.data.db.entities.OperationEntity
import ua.graviton.isida.data.db.entities.RoleEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RolesRepository @Inject constructor(
    private val db: AppDatabase
) {

    suspend fun getRole(id: Long) = withContext(dispatcher) { db.rolesDao().role(id) }

    suspend fun getRoles() = withContext(dispatcher) { db.rolesDao().roles() }
    fun listenRoles() = db.rolesDao().listenRoles()

    suspend fun getRolesWithOperations() = withContext(dispatcher) { db.rolesDao().rolesWithOperations() }
    fun listenRolesWithOperations() = db.rolesDao().listenRolesWithOperations()


    suspend fun authRole(name: String, pass: String) = withContext(dispatcher) { db.rolesDao().roleWithOperations(name, pass) }


    suspend fun enableOperationForRole(roleId: Long, operation: String) = withContext(dispatcher) {
        val newRoleOperation = OperationEntity(name = operation, roleId = roleId)
        db.operationsDao().insert(newRoleOperation)
    }

    suspend fun disableOperationForRole(roleId: Long, operation: String) = withContext(dispatcher) {
        db.operationsDao().delete(roleId, operation)
    }


    suspend fun addRole(
        name: String,
        pass: String
    ) = withContext(dispatcher) {
        //TODO: instead of raw pass we have to store pass hash, but for test we gonna use direct password
        val role = RoleEntity(name = name, passHash = pass)
        val id = db.rolesDao().insert(role)
        role.copy(id = id)
    }

    suspend fun removeRole(
        roleId: Long,
    ) = withContext(dispatcher) {
        db.rolesDao().delete(roleId) > 0
    }


    companion object {
        private val dispatcher = Dispatchers.IO
    }
}