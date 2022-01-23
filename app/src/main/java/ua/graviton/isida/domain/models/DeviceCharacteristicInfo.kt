package ua.graviton.isida.domain.models

sealed class DeviceCharacteristicInfo<T>(
    val id: String,
    val limits: List<Limit<T>> = emptyList(),
) {

    object Unknown : DeviceCharacteristicInfo<Unit>(id = "unknown")

    object SpT0 : DeviceCharacteristicInfo<Float>(
        id = "spT0",
        limits = listOf(Limit.FloatMinMaxLimit(12f, 42f)),
    )

    object SpT1 : DeviceCharacteristicInfo<Float>(
        id = "spT1",
        limits = listOf(Limit.FloatMinMaxLimit(12f, 42f)),
    )

    object SpRh0 : DeviceCharacteristicInfo<Float>(
        id = "spRh0",
    )

    object SpRh1 : DeviceCharacteristicInfo<Float>(
        id = "spRh1",
    )

    object K0 : DeviceCharacteristicInfo<Int>(
        id = "K0",
    )

    object K1 : DeviceCharacteristicInfo<Int>(
        id = "K1",
    )


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


    companion object {
        fun byId(id: String): DeviceCharacteristicInfo<*> = when (id) {
            "spT0" -> SpT0
            "spT1" -> SpT1
            "spRh0" -> SpRh0
            "spRh1" -> SpRh1
            "K0" -> K0
            "K1" -> K1
            else -> Unknown
        }
    }
}