package ua.graviton.isida.domain.models

sealed class DeviceCharacteristicInfo<T>(
    val limits: List<Limit<T>> = emptyList(),
) {
    object Unknown : DeviceCharacteristicInfo<Unit>()
    object SpT : DeviceCharacteristicInfo<Float>(
        limits = listOf(Limit.MinMax(25f, 40f)),
    )

    object Fl01_10 : DeviceCharacteristicInfo<Float>(
        limits = listOf(Limit.MinMax(0.1f, 10.0f)),
    )

    object SpRh : DeviceCharacteristicInfo<Float>(
        limits = listOf(Limit.MinMax(20f, 80f)),
    )

    object Min0 : DeviceCharacteristicInfo<Int>(
        limits = listOf(Limit.Min(0)),
    )

    object Min1 : DeviceCharacteristicInfo<Int>(
        limits = listOf(Limit.Min(1))
    )

    object Min10 : DeviceCharacteristicInfo<Int>(
        limits = listOf(Limit.Min(10))
    )

    object Min100 : DeviceCharacteristicInfo<Int>(
        limits = listOf(Limit.Min(100))
    )

    object Min1000 : DeviceCharacteristicInfo<Int>(
        limits = listOf(Limit.Min(1000))
    )

    object Max1 : DeviceCharacteristicInfo<Int>(
        limits = listOf(Limit.MinMax(0, 1))
    )

    object Max3 : DeviceCharacteristicInfo<Int>(
        limits = listOf(Limit.MinMax(0, 3))
    )

    object Max5 : DeviceCharacteristicInfo<Int>(
        limits = listOf(Limit.MinMax(0, 5))
    )

    object Id : DeviceCharacteristicInfo<Int>(
        limits = listOf(Limit.MinMax(0, 30))
    )

    object Int1_100 : DeviceCharacteristicInfo<Int>(
        limits = listOf(Limit.MinMax(1, 100))
    )

    object Int10_1000 : DeviceCharacteristicInfo<Int>(
        limits = listOf(Limit.MinMax(10, 1000))
    )
    /*object K1 : DeviceCharacteristicInfo<Int>(
        limits = listOf(
            Limit.Max(10),
            Limit.MinMax(6, 9)
        )
    )*/


    sealed interface Limit<in T> {
        fun isValid(value: T): Boolean

        data class MinMax<T : Number>(val min: T, val max: T) : Limit<T> {
            override fun isValid(value: T): Boolean {
                return when (value) {
                    is Int -> (value <= (max as Int)) && (value >= (min as Int))
                    is Float -> (value <= (max as Float)) && (value >= (min as Float))
                    else -> true
                }
            }
        }

        data class Max<T : Number>(val max: T) : Limit<T> {
            override fun isValid(value: T): Boolean {
                return when (value) {
                    is Int -> value <= (max as Int)
                    is Float -> value <= (max as Float)
                    else -> true
                }
            }
        }

        data class Min<T : Number>(val min: T) : Limit<T> {
            override fun isValid(value: T): Boolean {
                return when (value) {
                    is Int -> value >= (min as Int)
                    is Float -> value >= (min as Float)
                    else -> true
                }
            }
        }
    }
}