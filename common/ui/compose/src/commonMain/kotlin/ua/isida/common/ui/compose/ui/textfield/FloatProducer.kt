package ua.isida.common.ui.compose.ui.textfield

/**
 * Alternative to `() -> Float` but avoids boxing.
 *
 * !!! Do not use in public APIs !!!
 */
internal fun interface FloatProducer {
    /** Returns the Float. */
    operator fun invoke(): Float
}