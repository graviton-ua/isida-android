package ua.graviton.isida.data.db.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ua.graviton.isida.data.db.entities.RoleEntity
import ua.graviton.isida.data.db.resultentities.RoleWithOperations

@Dao
abstract class RolesDao : EntityDao<RoleEntity>() {

    @Query("SELECT * FROM roles")
    abstract suspend fun roles(): List<RoleEntity>

    @Query("SELECT * FROM roles WHERE id = :id")
    abstract suspend fun role(id: Long): RoleEntity?

    @Transaction
    @Query("SELECT * FROM roles")
    abstract suspend fun rolesWithOperations(): List<RoleWithOperations>

    @Transaction
    @Query("SELECT * FROM roles WHERE id = :id")
    abstract suspend fun roleWithOperations(id: Long): RoleWithOperations?


    @Query("SELECT * FROM roles")
    abstract fun listenRoles(): Flow<List<RoleEntity>>

    @Query("SELECT * FROM roles WHERE id = :id")
    abstract fun listenRole(id: Long): Flow<RoleEntity?>

    @Transaction
    @Query("SELECT * FROM roles")
    abstract fun listenRolesWithOperations(): Flow<List<RoleWithOperations>>

    @Transaction
    @Query("SELECT * FROM roles WHERE id = :id")
    abstract fun listenRoleWithOperations(id: Long): Flow<RoleWithOperations?>


    @Query("DELETE FROM roles WHERE id = :id")
    abstract suspend fun delete(id: Long): Int
}