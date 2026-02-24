package ua.isida.common.ui.compose.ui.textfield

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable

internal enum class MotionSchemeKeyTokens {
    DefaultSpatial,
    FastSpatial,
    SlowSpatial,
    DefaultEffects,
    FastEffects,
    SlowEffects,
}

/**
 * Helper function for component motion tokens.
 *
 * Here is an example on how to use component motion tokens:
 * ``MaterialTheme.motionScheme.fromToken(ExtendedFabBranded.ExpandMotion)``
 *
 * The returned [FiniteAnimationSpec] is remembered across compositions.
 *
 * @param value the token's value
 */
@Stable
internal fun <T> fromToken(value: MotionSchemeKeyTokens): FiniteAnimationSpec<T> {
    return when (value) {
        MotionSchemeKeyTokens.DefaultSpatial -> StandardMotionSchemeImpl.defaultSpatialSpec()
        MotionSchemeKeyTokens.FastSpatial -> StandardMotionSchemeImpl.fastSpatialSpec()
        MotionSchemeKeyTokens.SlowSpatial -> StandardMotionSchemeImpl.slowSpatialSpec()
        MotionSchemeKeyTokens.DefaultEffects -> StandardMotionSchemeImpl.defaultEffectsSpec()
        MotionSchemeKeyTokens.FastEffects -> StandardMotionSchemeImpl.fastEffectsSpec()
        MotionSchemeKeyTokens.SlowEffects -> StandardMotionSchemeImpl.slowEffectsSpec()
    }
}

/**
 * Converts a [MotionSchemeKeyTokens] key to the [FiniteAnimationSpec] provided by the
 * [MotionScheme].
 */
@Composable
@ReadOnlyComposable
internal fun <T> MotionSchemeKeyTokens.value(): FiniteAnimationSpec<T> = fromToken(this)


@Suppress("UNCHECKED_CAST")
private object StandardMotionSchemeImpl {
    private object StandardMotionTokens {
        val SpringDefaultSpatialDamping = 0.9f
        val SpringDefaultSpatialStiffness = 700.0f
        val SpringDefaultEffectsDamping = 1.0f
        val SpringDefaultEffectsStiffness = 1600.0f
        val SpringFastSpatialDamping = 0.9f
        val SpringFastSpatialStiffness = 1400.0f
        val SpringFastEffectsDamping = 1.0f
        val SpringFastEffectsStiffness = 3800.0f
        val SpringSlowSpatialDamping = 0.9f
        val SpringSlowSpatialStiffness = 300.0f
        val SpringSlowEffectsDamping = 1.0f
        val SpringSlowEffectsStiffness = 800.0f
    }

    private val defaultSpatialSpec =
        spring<Any>(
            dampingRatio = StandardMotionTokens.SpringDefaultSpatialDamping,
            stiffness = StandardMotionTokens.SpringDefaultSpatialStiffness,
        )

    private val fastSpatialSpec =
        spring<Any>(
            dampingRatio = StandardMotionTokens.SpringFastSpatialDamping,
            stiffness = StandardMotionTokens.SpringFastSpatialStiffness,
        )

    private val slowSpatialSpec =
        spring<Any>(
            dampingRatio = StandardMotionTokens.SpringSlowSpatialDamping,
            stiffness = StandardMotionTokens.SpringSlowSpatialStiffness,
        )

    private val defaultEffectsSpec =
        spring<Any>(
            dampingRatio = StandardMotionTokens.SpringDefaultEffectsDamping,
            stiffness = StandardMotionTokens.SpringDefaultEffectsStiffness,
        )

    private val fastEffectsSpec =
        spring<Any>(
            dampingRatio = StandardMotionTokens.SpringFastEffectsDamping,
            stiffness = StandardMotionTokens.SpringFastEffectsStiffness,
        )

    private val slowEffectsSpec =
        spring<Any>(
            dampingRatio = StandardMotionTokens.SpringSlowEffectsDamping,
            stiffness = StandardMotionTokens.SpringSlowEffectsStiffness,
        )

    fun <T> defaultSpatialSpec(): FiniteAnimationSpec<T> {
        return defaultSpatialSpec as FiniteAnimationSpec<T>
    }

    fun <T> fastSpatialSpec(): FiniteAnimationSpec<T> {
        return fastSpatialSpec as FiniteAnimationSpec<T>
    }

    fun <T> slowSpatialSpec(): FiniteAnimationSpec<T> {
        return slowSpatialSpec as FiniteAnimationSpec<T>
    }

    fun <T> defaultEffectsSpec(): FiniteAnimationSpec<T> {
        return defaultEffectsSpec as FiniteAnimationSpec<T>
    }

    fun <T> fastEffectsSpec(): FiniteAnimationSpec<T> {
        return fastEffectsSpec as FiniteAnimationSpec<T>
    }

    fun <T> slowEffectsSpec(): FiniteAnimationSpec<T> {
        return slowEffectsSpec as FiniteAnimationSpec<T>
    }
}