package ua.graviton.isida.domain.models

sealed class DeviceCharacteristicInfo<T>(
    val limits: List<Limit<T>> = emptyList(),
) {

    object Unknown : DeviceCharacteristicInfo<Unit>()

    object SpT0 : DeviceCharacteristicInfo<Float>(
        limits = listOf(Limit.FloatMinMaxLimit(12f, 42f)),
    )

    object SpT1 : DeviceCharacteristicInfo<Float>(
        limits = listOf(Limit.FloatMinMaxLimit(12f, 42f)),
    )

    object SpRh0 : DeviceCharacteristicInfo<Float>()

    object SpRh1 : DeviceCharacteristicInfo<Float>()

    object K0 : DeviceCharacteristicInfo<Int>()

    object K1 : DeviceCharacteristicInfo<Int>()


    sealed interface Limit<in T> {
        fun isValid(value: T): Boolean

        data class IntMinMaxLimit(val min: Int = Int.MIN_VALUE, val max: Int = Int.MAX_VALUE) : Limit<Int> {
            override fun isValid(value: Int): Boolean = (value < max) && (value > min)
        }

        data class IntMaxLimit(val max: Int = Int.MAX_VALUE) : Limit<Int> {
            override fun isValid(value: Int): Boolean = value < max
        }

        data class IntMinLimit(val min: Int = Int.MIN_VALUE) : Limit<Int> {
            override fun isValid(value: Int): Boolean = value > min
        }


        data class FloatMinMaxLimit(val min: Float = Float.MIN_VALUE, val max: Float = Float.MAX_VALUE) : Limit<Float> {
            override fun isValid(value: Float): Boolean = (value < max) && (value > min)
        }

        data class FloatMaxLimit(val max: Float = Float.MAX_VALUE) : Limit<Float> {
            override fun isValid(value: Float): Boolean = value < max
        }

        data class FloatMinLimit(val min: Float = Float.MIN_VALUE) : Limit<Float> {
            override fun isValid(value: Float): Boolean = value > min
        }
    }

    sealed interface Enum<in T> {

        data class IntEnum(
            val list: List<Int>,
            val map: (Int) -> String,
        ) : Enum<Int>
    }
}