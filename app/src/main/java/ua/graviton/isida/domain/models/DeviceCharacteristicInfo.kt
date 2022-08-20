package ua.graviton.isida.domain.models

sealed class DeviceCharacteristicInfo<T>(
    val limits: List<Limit<T>> = emptyList(),
) {

    object Unknown : DeviceCharacteristicInfo<Unit>()

    object Temperature : DeviceCharacteristicInfo<Float>(
        limits = listOf(Limit.MinMax(12f, 42f)),
    )

    object SpRh : DeviceCharacteristicInfo<Float>()
    object K : DeviceCharacteristicInfo<Int>(limits = listOf(Limit.Min(10)))
    object K1 : DeviceCharacteristicInfo<Int>(limits = listOf(Limit.Max(10), Limit.MinMax(6, 9)))
    object Alarm : DeviceCharacteristicInfo<Float>()


    sealed interface Limit<in T> {
        fun isValid(value: T): Boolean

        data class MinMax<T : Number>(val min: T, val max: T) : Limit<T> {
            override fun isValid(value: T): Boolean {
                return when (value) {
                    is Int -> (value < (max as Int)) && (value > (min as Int))
                    is Float -> (value < (max as Float)) && (value > (min as Float))
                    else -> true
                }
            }
        }

        data class Max<T : Number>(val max: T) : Limit<T> {
            override fun isValid(value: T): Boolean {
                return when (value) {
                    is Int -> value < (max as Int)
                    is Float -> value < (max as Float)
                    else -> true
                }
            }
        }

        data class Min<T : Number>(val min: T) : Limit<T> {
            override fun isValid(value: T): Boolean {
                return when (value) {
                    is Int -> value > (min as Int)
                    is Float -> value > (min as Float)
                    else -> true
                }
            }
        }
    }
}