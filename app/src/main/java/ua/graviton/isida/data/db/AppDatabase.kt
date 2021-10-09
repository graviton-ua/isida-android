package ua.graviton.isida.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.graviton.isida.data.db.daos.DeviceDataDao
import ua.graviton.isida.data.db.entities.DeviceDataEntity

@Database(
    entities = [
        DeviceDataEntity::class,
    ],
    version = 1
)
@TypeConverters(DbTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deviceDataDao(): DeviceDataDao
}