package ua.graviton.isida.data.bl.model

data class SendPackageDto(
    val deviceType: Int,
    val deviceNumber: Int,
    val data: ByteArray,
) {

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
}