package ua.graviton.isida.data.bl.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SendPackageDto(
    val deviceType: Int,
    val deviceNumber: Int,
    val data: ByteArray,
) : Parcelable {

    fun asByteArray(): ByteArray {
        return ByteArray(4 + data.size + 2).apply {
            // Put basic info about command device type/number
            this[0] = deviceNumber.toByte()
            this[1] = deviceType.toByte()

            // Put length of our data
            this[2] = (data.size % 256).toByte()
            this[3] = (data.size / 256).toByte()

            // Put data into our output byte array
            data.forEachIndexed { index, byte -> this[4 + index] = byte }

            // Calculate simple CRC
            var crc = 0
            for (i in 0..this.size - 2) {
                crc += this[i]
                crc = crc xor (crc shr 2)
            }
            this[4 + data.size] = (crc % 256).toByte()
            this[4 + data.size + 1] = (crc / 256).toByte()
        }
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