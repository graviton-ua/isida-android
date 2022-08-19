package ua.graviton.isida.domain.models

sealed class DeviceCharacteristicInfo<T>(
    val limits: List<Limit<T>> = emptyList(),
) {

    object Unknown : DeviceCharacteristicInfo<Unit>()

    object SpT : DeviceCharacteristicInfo<Float>(
        limits = listOf(Limit.MinMax(12f, 42f)),
    )

    object SpRh : DeviceCharacteristicInfo<Float>()
    object K : DeviceCharacteristicInfo<Int>()
    object Alarm : DeviceCharacteristicInfo<Float>()


    sealed interface Limit<in T> {
        fun isValid(value: T): Boolean

        data class MinMax<T : Number>(val min: T, val max: T) : Limit<T> {
            override fun isValid(value: T): Boolean {
                return when (value::class) {
                    Int::class -> ((value as Int) < (max as Int)) && ((value as Int) > (min as Int))
                    Float::class -> ((value as Float) < (max as Float)) && ((value as Float) > (min as Float))
                    else -> true
                }
            }
        }

        data class Max<T : Number>(val max: T) : Limit<T> {
            override fun isValid(value: T): Boolean {
                return when (value::class) {
                    Int::class -> (value as Int) < (max as Int)
                    Float::class -> (value as Float) < (max as Float)
                    else -> true
                }
            }
        }

        data class Min<T : Number>(val max: T) : Limit<T> {
            override fun isValid(value: T): Boolean {
                return when (value::class) {
                    Int::class -> (value as Int) > (max as Int)
                    Float::class -> (value as Float) > (max as Float)
                    else -> true
                }
            }
        }
    }
}