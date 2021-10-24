package ua.graviton.isida.data.db.daos

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.graviton.isida.data.db.entities.DeviceDataEntity

@Dao
abstract class DeviceDataDao : EntityDao<DeviceDataEntity>() {

    @Query("SELECT * from data ORDER BY date(created_at) DESC LIMIT 1")
    abstract suspend fun getLatest(): List<DeviceDataEntity>

    @Query("SELECT * from data ORDER BY date(created_at) DESC LIMIT 1")
    abstract fun observeLatest(): Flow<List<DeviceDataEntity>>

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    abstract suspend fun insertOrIgnore(entity: DayEntity): Long

//    suspend fun insertOrUpdate(entity: DayEntity) {
//        val id = insertOrIgnore(entity)
//        if (id == -1L) update(entity)
//    }

//    @Transaction
//    open suspend fun insertOrUpdate(entities: List<DayEntity>) = entities.forEach { insertOrUpdate(it) }
}