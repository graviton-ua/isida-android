package ua.graviton.isida.data.db.models

data class RoleEntity(
    val id: Long = 0,
    val name: String,
    val passHash: String,
)