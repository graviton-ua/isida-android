package ua.graviton.isida.data.db.daos

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.graviton.isida.data.db.entities.OperationEntity

@Dao
abstract class OperationsDao : EntityDao<OperationEntity>() {

    @Query("SELECT * FROM operations")
    abstract suspend fun operations(): List<OperationEntity>

    @Query("SELECT * FROM operations WHERE role_id = :roleId")
    abstract suspend fun operations(roleId: Long): List<OperationEntity>


    @Query("SELECT * FROM operations")
    abstract fun listenOperations(): Flow<List<OperationEntity>>

    @Query("SELECT * FROM operations WHERE role_id = :roleId")
    abstract fun listenOperations(roleId: Long): Flow<List<OperationEntity>>


    @Query("DELETE FROM operations WHERE id = :id")
    abstract suspend fun delete(id: Long): Int

    @Query("DELETE FROM operations WHERE role_id = :roleId AND name = :name")
    abstract suspend fun delete(roleId: Long, name: String): Int
}