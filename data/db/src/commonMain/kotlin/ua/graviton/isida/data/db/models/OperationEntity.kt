package ua.graviton.isida.data.db.models

data class OperationEntity(
    val id: Long = 0,
    val name: String,
    val roleId: Long,
)