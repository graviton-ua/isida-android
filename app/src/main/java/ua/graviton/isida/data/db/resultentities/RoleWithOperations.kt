package ua.graviton.isida.data.db.resultentities

import androidx.room.Embedded
import androidx.room.Relation
import ua.graviton.isida.data.db.entities.OperationEntity
import ua.graviton.isida.data.db.entities.RoleEntity
import java.util.*

class RoleWithOperations {
    @Embedded
    var role: RoleEntity? = null

    @Relation(parentColumn = "id", entityColumn = "role_id")
    var operations: List<OperationEntity> = emptyList()

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is RoleWithOperations -> role == other.role && operations == other.operations
        else -> false
    }

    override fun hashCode(): Int = Objects.hash(role, operations)
}