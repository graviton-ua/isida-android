package com.whoppah.common.compose.ui.textfield

import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.LayoutIdParentData
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints

internal val IntrinsicMeasurable.layoutId: Any?
    get() = (parentData as? LayoutIdParentData)?.layoutId

internal val Placeable?.widthOrZero: Int
    get() = this?.width ?: 0

internal val Placeable?.heightOrZero: Int
    get() = this?.height ?: 0

/**
 * Subtracts one value from another, where both values represent constraints used in layout.
 *
 * Notably:
 * - if [this] is [Constraints.Companion.Infinity], the result stays [Constraints.Companion.Infinity]
 * - the result is coerced to be non-negative
 */
internal fun Int.subtractConstraintSafely(other: Int): Int {
    if (this == Constraints.Infinity) {
        return this
    }
    return (this - other).coerceAtLeast(0)
}