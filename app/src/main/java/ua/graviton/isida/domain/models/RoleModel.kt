package ua.graviton.isida.domain.models

import ua.graviton.isida.data.db.entities.RoleEntity
import ua.graviton.isida.data.db.resultentities.RoleWithOperations

data class RoleModel(
    val id: Long,
    val name: String,
    val operations: List<String> = emptyList(),
)

fun RoleEntity.toDomain(): RoleModel {
    return RoleModel(
        id = id,
        name = name,
        operations = emptyList()
    )
}

fun RoleWithOperations.toDomain(): RoleModel? {
    val role = role ?: return null
    return RoleModel(
        id = role.id,
        name = role.name,
        operations = operations.map { it.name }
    )
}