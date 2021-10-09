package ua.graviton.isida.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.graviton.isida.data.bl.model.DataPackageDto
import java.time.OffsetDateTime

@Entity(tableName = "data")
data class DeviceDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "created_at") val createdAt: OffsetDateTime = OffsetDateTime.now(),

    @ColumnInfo(name = "device_id") val deviceId: Int,
    @ColumnInfo(name = "pv_t0") val pvT0: Float,
    @ColumnInfo(name = "pv_t1") val pvT1: Float,
    @ColumnInfo(name = "pv_t2") val pvT2: Float,
    @ColumnInfo(name = "pv_t3") val pvT3: Float,
    @ColumnInfo(name = "pv_rh") val pvRh: Float,
    @ColumnInfo(name = "pv_co2_1") val pvCO2_1: Int,
    @ColumnInfo(name = "pv_co2_2") val pvCO2_2: Int,
    @ColumnInfo(name = "pv_co2_3") val pvCO2_3: Int,
    @ColumnInfo(name = "pv_timer") val pvTimer: Int,
    @ColumnInfo(name = "pv_tmr_count") val pvTmrCount: Int,
    @ColumnInfo(name = "pv_flap") val pvFlap: Int,
    @ColumnInfo(name = "power") val power: Int,
    @ColumnInfo(name = "fuses") val fuses: Int,
    @ColumnInfo(name = "errors") val errors: Int,
    @ColumnInfo(name = "warning") val warning: Int,
    @ColumnInfo(name = "cost0") val cost0: Int,
    @ColumnInfo(name = "cost1") val cost1: Int,
    @ColumnInfo(name = "date") val date: Int,
    @ColumnInfo(name = "hours") val hours: Int,
)

fun DataPackageDto.toEntity(): DeviceDataEntity {
    return DeviceDataEntity(
        deviceId = cellId,
        pvT0 = pvT0, pvT1 = pvT1,
        pvT2 = pvT2, pvT3 = pvT3,
        pvRh = pvRh,
        pvCO2_1 = pvCO2_1, pvCO2_2 = pvCO2_2,
        pvCO2_3 = pvCO2_3,
        pvTimer = pvTimer,
        pvTmrCount = pvTmrCount,
        pvFlap = pvFlap,
        power = power, fuses = fuses,
        errors = errors, warning = warning,
        cost0 = cost0, cost1 = cost1,
        date = date, hours = hours,
    )
}