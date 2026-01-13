package ua.graviton.isida.data.db.models

data class RoleWithOperations(
    val role: RoleEntity,
    val operations: List<OperationEntity>
)