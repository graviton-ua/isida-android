package ua.graviton.isida.data.db.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.whoppah.util.AppCoroutineDispatchers
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.graviton.isida.data.db.models.OperationEntity
import ua.graviton.isida.data.db.models.RoleEntity
import ua.graviton.isida.data.db.models.RoleWithOperations
import ua.graviton.isida.data.sql.Database

@Inject
@SingleIn(AppScope::class)
class RolesDao(
    dispatchers: AppCoroutineDispatchers,
    db: Database,
) : Dao(dispatcher = dispatchers.io, db = db) {

    private val queries = db.rolesQueries

    // --- Helper to Group Flat SQL Rows into Hierarchy ---
    // The query returns a generated type: SelectAllWithOperations(roles: Roles, operations: Operations?)
    private fun <T> List<T>.mapToRoleWithOperations(
        roleSelector: (T) -> RoleEntity,
        opSelector: (T) -> OperationEntity?
    ): List<RoleWithOperations> {
        return this.groupBy(roleSelector)
            .map { (role, rows) ->
                RoleWithOperations(
                    role = role,
                    operations = rows.mapNotNull(opSelector)
                )
            }
    }

    // --- Suspend Functions ---

    suspend fun getRoles(): List<RoleEntity> = queries.selectAll(mapper = mapper).executeAsList()

    suspend fun getRole(id: Long): RoleEntity? = queries.selectById(id, mapper = mapper).executeAsOneOrNull()

    suspend fun getRoleWithOperations(id: Long): RoleWithOperations? {
        val rows = queries.selectByIdWithOperations(id).executeAsList()
        // Since we filtered by ID, we expect 0 or 1 Role (but multiple rows if multiple ops)
        return rows.mapToRoleWithOperations(
            { RoleEntity(it.id, it.name, it.pass_hash) },
            {
                OperationEntity(
                    it.id_ ?: return@mapToRoleWithOperations null,
                    it.name_ ?: return@mapToRoleWithOperations null,
                    it.role_id ?: return@mapToRoleWithOperations null,
                )
            },
        )
            .firstOrNull()
    }

    suspend fun getRoleWithOperations(name: String, pass: String): RoleWithOperations? {
        val rows = queries.selectByCredsWithOperations(name, pass).executeAsList()
        return rows.mapToRoleWithOperations(
            { RoleEntity(it.id, it.name, it.pass_hash) },
            {
                OperationEntity(
                    it.id_ ?: return@mapToRoleWithOperations null,
                    it.name_ ?: return@mapToRoleWithOperations null,
                    it.role_id ?: return@mapToRoleWithOperations null,
                )
            },
        )
            .firstOrNull()
    }

    suspend fun getRolesWithOperations(): List<RoleWithOperations> {
        val rows = queries.selectAllWithOperations().executeAsList()
        return rows.mapToRoleWithOperations(
            { RoleEntity(it.id, it.name, it.pass_hash) },
            {
                OperationEntity(
                    it.id_ ?: return@mapToRoleWithOperations null,
                    it.name_ ?: return@mapToRoleWithOperations null,
                    it.role_id ?: return@mapToRoleWithOperations null,
                )
            },
        )
    }

    suspend fun delete(id: Long) {
        queries.deleteById(id)
    }

    // --- Flow Observables ---

    fun listenRoles(): Flow<List<RoleEntity>> {
        return queries.selectAll(mapper = mapper)
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    fun listenRole(id: Long): Flow<RoleEntity?> {
        return queries.selectById(id, mapper = mapper)
            .asFlow()
            .mapToList(Dispatchers.IO) // Get list to handle 0 results safely
            .map { it.firstOrNull() }
    }

    fun listenRolesWithOperations(): Flow<List<RoleWithOperations>> {
        return queries.selectAllWithOperations()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { rows ->
                rows.mapToRoleWithOperations(
                    { RoleEntity(it.id, it.name, it.pass_hash) },
                    {
                        OperationEntity(
                            it.id_ ?: return@mapToRoleWithOperations null,
                            it.name_ ?: return@mapToRoleWithOperations null,
                            it.role_id ?: return@mapToRoleWithOperations null,
                        )
                    },
                )
            }
    }

    fun listenRoleWithOperations(id: Long): Flow<RoleWithOperations?> {
        return queries.selectByIdWithOperations(id)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { rows ->
                rows.mapToRoleWithOperations(
                    { RoleEntity(it.id, it.name, it.pass_hash) },
                    {
                        OperationEntity(
                            it.id_ ?: return@mapToRoleWithOperations null,
                            it.name_ ?: return@mapToRoleWithOperations null,
                            it.role_id ?: return@mapToRoleWithOperations null,
                        )
                    },
                )
                    .firstOrNull()
            }
    }
}

private val mapper: (
    id: Long,
    name: String,
    pass_hash: String,
) -> RoleEntity = ::RoleEntity