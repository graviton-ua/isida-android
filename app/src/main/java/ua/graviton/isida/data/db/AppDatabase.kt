package ua.graviton.isida.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.graviton.isida.data.db.daos.DeviceDataDao
import ua.graviton.isida.data.db.daos.OperationsDao
import ua.graviton.isida.data.db.daos.RolesDao
import ua.graviton.isida.data.db.entities.DeviceDataEntity
import ua.graviton.isida.data.db.entities.OperationEntity
import ua.graviton.isida.data.db.entities.RoleEntity

@Database(
    entities = [
        DeviceDataEntity::class,
        RoleEntity::class,
        OperationEntity::class,
    ],
    version = 1
)
@TypeConverters(DbTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deviceDataDao(): DeviceDataDao
    abstract fun rolesDao(): RolesDao
    abstract fun operationsDao(): OperationsDao
}