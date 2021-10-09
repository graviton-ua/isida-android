package ua.graviton.isida.data.db.daos

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ua.graviton.isida.data.db.entities.DeviceDataEntity

@Dao
abstract class DeviceDataDao {

    @Query("SELECT * from data ORDER BY date(created_at) DESC LIMIT 1")
    abstract suspend fun getLatest(): List<DeviceDataEntity>

    @Query("SELECT * from data ORDER BY date(created_at) DESC LIMIT 1")
    abstract fun observeLatest(): Flow<List<DeviceDataEntity>>


    @Insert
    abstract suspend fun insert(entity: DeviceDataEntity): Long

    @Insert
    abstract suspend fun insertAll(entities: List<DeviceDataEntity>): LongArray

    @Update
    abstract suspend fun update(entity: DeviceDataEntity)

    @Delete
    abstract suspend fun delete(entity: DeviceDataEntity): Int


//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    abstract suspend fun insertOrIgnore(entity: DayEntity): Long

//    suspend fun insertOrUpdate(entity: DayEntity) {
//        val id = insertOrIgnore(entity)
//        if (id == -1L) update(entity)
//    }

//    @Transaction
//    open suspend fun insertOrUpdate(entities: List<DayEntity>) = entities.forEach { insertOrUpdate(it) }
}