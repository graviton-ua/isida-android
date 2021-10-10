package ua.graviton.isida.data.db.entities

import androidx.room.*

@Entity(
    tableName = "operations",
    indices = [
        Index(value = ["role_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = RoleEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("role_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OperationEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "role_id") val roleId: Long,
) : IEntity