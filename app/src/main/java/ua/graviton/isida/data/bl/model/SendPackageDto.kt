package ua.graviton.isida.data.bl.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ua.graviton.isida.data.bl.CRC16
import ua.graviton.isida.data.bl.IsidaCommands
import ua.graviton.isida.utils.asByteArray

/**
 *  Basic class that describes command that should be sent to klimat/isida device
 *  Factory class that produce objects are [IsidaCommands]
 */
@Parcelize
data class SendPackageDto(
    val deviceType: Int,
    val deviceNumber: Int,
    val data: ByteArray,
) : Parcelable {

    fun asByteArray(): ByteArray {
        val head = byteArrayOf(deviceNumber.toByte(), deviceType.toByte())
        val dataLength = data.size.toShort().asByteArray()
        val basicData = head + dataLength + data
        val crc = CRC16.crcSimple(basicData).toShort().asByteArray()
        return basicData + crc
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SendPackageDto

        if (deviceType != other.deviceType) return false
        if (deviceNumber != other.deviceNumber) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = deviceType
        result = 31 * result + deviceNumber
        result = 31 * result + data.contentHashCode()
        return result
    }
}