package ua.graviton.isida.data.db.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.whoppah.util.AppCoroutineDispatchers
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import ua.graviton.isida.data.db.models.OperationEntity
import ua.graviton.isida.data.sql.Database

@Inject
@SingleIn(AppScope::class)
class OperationsDao(
    dispatchers: AppCoroutineDispatchers,
    db: Database,
) : Dao(dispatcher = dispatchers.io, db = db) {

    private val queries = db.operationsQueries

    // --- Suspend Functions ---

    suspend fun getOperations(): List<OperationEntity> {
        return queries.selectAll(mapper = mapper).executeAsList()
    }

    suspend fun getOperations(roleId: Long): List<OperationEntity> {
        return queries.selectByRoleId(roleId, mapper = mapper).executeAsList()
    }

    suspend fun isAllowed(roleId: Long, operationName: String): OperationEntity? {
        return queries.selectAllowedOperation(roleId, operationName, mapper = mapper)
            .executeAsOneOrNull()
    }

    suspend fun delete(id: Long) {
        queries.deleteById(id)
    }

    suspend fun delete(roleId: Long, name: String) {
        queries.deleteByRoleAndName(roleId, name)
    }

    suspend fun insert(name: String, roleId: Long) {
        queries.insertOperation(name, roleId)
    }

    // --- Flow Observables ---

    fun listenOperations(): Flow<List<OperationEntity>> {
        return queries.selectAll(mapper = mapper)
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    fun listenOperations(roleId: Long): Flow<List<OperationEntity>> {
        return queries.selectByRoleId(roleId, mapper = mapper)
            .asFlow()
            .mapToList(Dispatchers.IO)
    }
}

private val mapper: (
    id: Long,
    name: String,
    role_id: Long,
) -> OperationEntity = ::OperationEntity