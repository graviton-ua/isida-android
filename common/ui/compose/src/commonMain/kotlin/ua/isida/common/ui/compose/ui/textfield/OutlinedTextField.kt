package ua.isida.common.ui.compose.ui.textfield

import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.*
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.*
import androidx.compose.ui.util.fastFirst
import androidx.compose.ui.util.fastFirstOrNull
import androidx.compose.ui.util.lerp
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * Layout of the leading and trailing icons and the text field, label and placeholder in
 * [OutlinedTextField]. It doesn't use Row to position the icons and middle part because label
 * should not be positioned in the middle part.
 */
@Composable
internal fun OutlinedTextFieldLayout(
    modifier: Modifier,
    textField: @Composable () -> Unit,
    placeholder: @Composable ((Modifier) -> Unit)?,
    label: @Composable (() -> Unit)?,
    leading: @Composable (() -> Unit)?,
    trailing: @Composable (() -> Unit)?,
    prefix: @Composable (() -> Unit)?,
    suffix: @Composable (() -> Unit)?,
    singleLine: Boolean,
    labelPosition: TextFieldLabelPosition,
    labelProgress: FloatProducer,
    onLabelMeasured: (Size) -> Unit,
    container: @Composable () -> Unit,
    supporting: @Composable (() -> Unit)?,
    paddingValues: PaddingValues,
) {
    val horizontalIconPadding = textFieldHorizontalIconPadding()
    val measurePolicy =
        remember(
            onLabelMeasured,
            singleLine,
            labelPosition,
            labelProgress,
            paddingValues,
            horizontalIconPadding,
        ) {
            OutlinedTextFieldMeasurePolicy(
                onLabelMeasured = onLabelMeasured,
                singleLine = singleLine,
                labelPosition = labelPosition,
                labelProgress = labelProgress,
                paddingValues = paddingValues,
                horizontalIconPadding = horizontalIconPadding,
            )
        }
    val layoutDirection = LocalLayoutDirection.current
    Layout(
        modifier = modifier,
        content = {
            container()

            if (leading != null) {
                Box(
                    modifier = Modifier
                        .layoutId(LeadingId)
                        .minimumInteractiveComponentSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    leading()
                }
            }
            if (trailing != null) {
                Box(
                    modifier = Modifier
                        .layoutId(TrailingId)
                        .minimumInteractiveComponentSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    trailing()
                }
            }

            val startTextFieldPadding = paddingValues.calculateStartPadding(layoutDirection)
            val endTextFieldPadding = paddingValues.calculateEndPadding(layoutDirection)

            val startPadding =
                if (leading != null) {
                    (startTextFieldPadding - horizontalIconPadding).coerceAtLeast(0.dp)
                } else {
                    startTextFieldPadding
                }
            val endPadding =
                if (trailing != null) {
                    (endTextFieldPadding - horizontalIconPadding).coerceAtLeast(0.dp)
                } else {
                    endTextFieldPadding
                }

            if (prefix != null) {
                Box(
                    Modifier
                        .layoutId(PrefixId)
                        .heightIn(min = MinTextLineHeight)
                        .wrapContentHeight()
                        .padding(start = startPadding, end = PrefixSuffixTextPadding)
                ) {
                    prefix()
                }
            }
            if (suffix != null) {
                Box(
                    Modifier
                        .layoutId(SuffixId)
                        .heightIn(min = MinTextLineHeight)
                        .wrapContentHeight()
                        .padding(start = PrefixSuffixTextPadding, end = endPadding)
                ) {
                    suffix()
                }
            }

            val textPadding =
                Modifier
                    .heightIn(min = MinTextLineHeight)
                    .wrapContentHeight()
                    .padding(
                        start = if (prefix == null) startPadding else 0.dp,
                        end = if (suffix == null) endPadding else 0.dp,
                    )

            if (placeholder != null) {
                placeholder(
                    Modifier
                        .layoutId(PlaceholderId)
                        .then(textPadding)
                )
            }

            Box(
                modifier = Modifier
                    .layoutId(TextFieldId)
                    .then(textPadding),
                propagateMinConstraints = true,
            ) {
                textField()
            }

            val labelPadding =
                if (labelPosition is TextFieldLabelPosition.Above) {
                    Modifier.padding(
                        start = AboveLabelHorizontalPadding,
                        end = AboveLabelHorizontalPadding,
                        bottom = AboveLabelBottomPadding,
                    )
                } else {
                    Modifier
                }

            if (label != null) {
                Box(
                    Modifier
                        .textFieldLabelMinHeight {
                            lerp(MinTextLineHeight, MinFocusedLabelLineHeight, labelProgress())
                        }
                        .wrapContentHeight()
                        .layoutId(LabelId)
                        .then(labelPadding)
                ) {
                    label()
                }
            }

            if (supporting != null) {
                Box(
                    Modifier
                        .layoutId(SupportingId)
                        .heightIn(min = MinSupportingTextLineHeight)
                        .wrapContentHeight()
                        .padding(TextFieldDefaults.supportingTextPadding())
                ) {
                    supporting()
                }
            }
        },
        measurePolicy = measurePolicy,
    )
}

private class OutlinedTextFieldMeasurePolicy(
    private val onLabelMeasured: (Size) -> Unit,
    private val singleLine: Boolean,
    private val labelPosition: TextFieldLabelPosition,
    private val labelProgress: FloatProducer,
    private val paddingValues: PaddingValues,
    private val horizontalIconPadding: Dp,
) : MeasurePolicy {
    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints,
    ): MeasureResult {
        val labelProgress = labelProgress()
        var occupiedSpaceHorizontally = 0
        var occupiedSpaceVertically = 0
        val bottomPadding = paddingValues.calculateBottomPadding().roundToPx()

        val relaxedConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        // measure leading icon
        val leadingPlaceable =
            measurables.fastFirstOrNull { it.layoutId == LeadingId }?.measure(relaxedConstraints)
        occupiedSpaceHorizontally += leadingPlaceable.widthOrZero
        occupiedSpaceVertically = max(occupiedSpaceVertically, leadingPlaceable.heightOrZero)

        // measure trailing icon
        val trailingPlaceable =
            measurables
                .fastFirstOrNull { it.layoutId == TrailingId }
                ?.measure(relaxedConstraints.offset(horizontal = -occupiedSpaceHorizontally))
        occupiedSpaceHorizontally += trailingPlaceable.widthOrZero
        occupiedSpaceVertically = max(occupiedSpaceVertically, trailingPlaceable.heightOrZero)

        // measure prefix
        val prefixPlaceable =
            measurables
                .fastFirstOrNull { it.layoutId == PrefixId }
                ?.measure(relaxedConstraints.offset(horizontal = -occupiedSpaceHorizontally))
        occupiedSpaceHorizontally += prefixPlaceable.widthOrZero
        occupiedSpaceVertically = max(occupiedSpaceVertically, prefixPlaceable.heightOrZero)

        // measure suffix
        val suffixPlaceable =
            measurables
                .fastFirstOrNull { it.layoutId == SuffixId }
                ?.measure(relaxedConstraints.offset(horizontal = -occupiedSpaceHorizontally))
        occupiedSpaceHorizontally += suffixPlaceable.widthOrZero
        occupiedSpaceVertically = max(occupiedSpaceVertically, suffixPlaceable.heightOrZero)

        // measure label
        val isLabelAbove = labelPosition is TextFieldLabelPosition.Above
        val labelMeasurable = measurables.fastFirstOrNull { it.layoutId == LabelId }
        var labelPlaceable: Placeable? = null
        val labelIntrinsicHeight: Int
        if (!isLabelAbove) {
            // if label is not Above, we can measure it like normal
            val totalHorizontalPadding =
                paddingValues.calculateLeftPadding(layoutDirection).roundToPx() +
                        paddingValues.calculateRightPadding(layoutDirection).roundToPx()
            val labelHorizontalConstraintOffset =
                lerp(
                    occupiedSpaceHorizontally + totalHorizontalPadding, // label in middle
                    totalHorizontalPadding, // label in outline
                    labelProgress,
                )
            val labelConstraints =
                relaxedConstraints.offset(
                    horizontal = -labelHorizontalConstraintOffset,
                    vertical = -bottomPadding,
                )
            labelPlaceable = labelMeasurable?.measure(labelConstraints)
            val labelSize =
                labelPlaceable?.let { Size(it.width.toFloat(), it.height.toFloat()) } ?: Size.Zero
            onLabelMeasured(labelSize)
            labelIntrinsicHeight = 0
        } else {
            // if label is Above, it must be measured after other elements, but we
            // reserve space for it using its intrinsic height as a heuristic
            labelIntrinsicHeight = labelMeasurable?.minIntrinsicHeight(constraints.minWidth) ?: 0
        }

        // supporting text must be measured after other elements, but we
        // reserve space for it using its intrinsic height as a heuristic
        val supportingMeasurable = measurables.fastFirstOrNull { it.layoutId == SupportingId }
        val supportingIntrinsicHeight =
            supportingMeasurable?.minIntrinsicHeight(constraints.minWidth) ?: 0

        // measure text field
        val topPadding =
            if (isLabelAbove) {
                paddingValues.calculateTopPadding().roundToPx()
            } else {
                max(
                    labelPlaceable.heightOrZero / 2,
                    paddingValues.calculateTopPadding().roundToPx(),
                )
            }
        val textConstraints =
            constraints
                .offset(
                    horizontal = -occupiedSpaceHorizontally,
                    vertical =
                        -bottomPadding -
                                topPadding -
                                labelIntrinsicHeight -
                                supportingIntrinsicHeight,
                )
                .copy(minHeight = 0)
        val textFieldPlaceable =
            measurables.fastFirst { it.layoutId == TextFieldId }.measure(textConstraints)

        // measure placeholder
        val placeholderConstraints = textConstraints.copy(minWidth = 0)
        val placeholderPlaceable =
            measurables
                .fastFirstOrNull { it.layoutId == PlaceholderId }
                ?.measure(placeholderConstraints)

        occupiedSpaceVertically =
            max(
                occupiedSpaceVertically,
                max(textFieldPlaceable.heightOrZero, placeholderPlaceable.heightOrZero) +
                        topPadding +
                        bottomPadding,
            )

        val width =
            calculateWidth(
                leadingPlaceableWidth = leadingPlaceable.widthOrZero,
                trailingPlaceableWidth = trailingPlaceable.widthOrZero,
                prefixPlaceableWidth = prefixPlaceable.widthOrZero,
                suffixPlaceableWidth = suffixPlaceable.widthOrZero,
                textFieldPlaceableWidth = textFieldPlaceable.width,
                labelPlaceableWidth = labelPlaceable.widthOrZero,
                placeholderPlaceableWidth = placeholderPlaceable.widthOrZero,
                constraints = constraints,
                labelProgress = labelProgress,
            )

        if (isLabelAbove) {
            // now that we know the width, measure label
            val labelConstraints =
                relaxedConstraints.copy(maxHeight = labelIntrinsicHeight, maxWidth = width)
            labelPlaceable = labelMeasurable?.measure(labelConstraints)
            val labelSize =
                labelPlaceable?.let { Size(it.width.toFloat(), it.height.toFloat()) } ?: Size.Zero
            onLabelMeasured(labelSize)
        }

        // measure supporting text
        val supportingConstraints =
            relaxedConstraints
                .offset(vertical = -occupiedSpaceVertically)
                .copy(minHeight = 0, maxWidth = width)
        val supportingPlaceable = supportingMeasurable?.measure(supportingConstraints)
        val supportingHeight = supportingPlaceable.heightOrZero

        val totalHeight =
            calculateHeight(
                leadingHeight = leadingPlaceable.heightOrZero,
                trailingHeight = trailingPlaceable.heightOrZero,
                prefixHeight = prefixPlaceable.heightOrZero,
                suffixHeight = suffixPlaceable.heightOrZero,
                textFieldHeight = textFieldPlaceable.height,
                labelHeight = labelPlaceable.heightOrZero,
                placeholderHeight = placeholderPlaceable.heightOrZero,
                supportingHeight = supportingPlaceable.heightOrZero,
                constraints = constraints,
                isLabelAbove = isLabelAbove,
                labelProgress = labelProgress,
            )
        val height =
            totalHeight - supportingHeight - (if (isLabelAbove) labelPlaceable.heightOrZero else 0)

        val containerPlaceable =
            measurables
                .fastFirst { it.layoutId == ContainerId }
                .measure(
                    Constraints(
                        minWidth = if (width != Constraints.Infinity) width else 0,
                        maxWidth = width,
                        minHeight = if (height != Constraints.Infinity) height else 0,
                        maxHeight = height,
                    )
                )
        return layout(width, totalHeight) {
            place(
                totalHeight = totalHeight,
                width = width,
                leadingPlaceable = leadingPlaceable,
                trailingPlaceable = trailingPlaceable,
                prefixPlaceable = prefixPlaceable,
                suffixPlaceable = suffixPlaceable,
                textFieldPlaceable = textFieldPlaceable,
                labelPlaceable = labelPlaceable,
                placeholderPlaceable = placeholderPlaceable,
                containerPlaceable = containerPlaceable,
                supportingPlaceable = supportingPlaceable,
                density = density,
                layoutDirection = layoutDirection,
                isLabelAbove = isLabelAbove,
                labelProgress = labelProgress,
                iconPadding = horizontalIconPadding.toPx(),
            )
        }
    }

    override fun IntrinsicMeasureScope.maxIntrinsicHeight(
        measurables: List<IntrinsicMeasurable>,
        width: Int,
    ): Int {
        return intrinsicHeight(measurables, width) { intrinsicMeasurable, w ->
            intrinsicMeasurable.maxIntrinsicHeight(w)
        }
    }

    override fun IntrinsicMeasureScope.minIntrinsicHeight(
        measurables: List<IntrinsicMeasurable>,
        width: Int,
    ): Int {
        return intrinsicHeight(measurables, width) { intrinsicMeasurable, w ->
            intrinsicMeasurable.minIntrinsicHeight(w)
        }
    }

    override fun IntrinsicMeasureScope.maxIntrinsicWidth(
        measurables: List<IntrinsicMeasurable>,
        height: Int,
    ): Int {
        return intrinsicWidth(measurables, height) { intrinsicMeasurable, h ->
            intrinsicMeasurable.maxIntrinsicWidth(h)
        }
    }

    override fun IntrinsicMeasureScope.minIntrinsicWidth(
        measurables: List<IntrinsicMeasurable>,
        height: Int,
    ): Int {
        return intrinsicWidth(measurables, height) { intrinsicMeasurable, h ->
            intrinsicMeasurable.minIntrinsicWidth(h)
        }
    }

    private fun IntrinsicMeasureScope.intrinsicWidth(
        measurables: List<IntrinsicMeasurable>,
        height: Int,
        intrinsicMeasurer: (IntrinsicMeasurable, Int) -> Int,
    ): Int {
        val textFieldWidth =
            intrinsicMeasurer(measurables.fastFirst { it.layoutId == TextFieldId }, height)
        val labelWidth =
            measurables
                .fastFirstOrNull { it.layoutId == LabelId }
                ?.let { intrinsicMeasurer(it, height) } ?: 0
        val trailingWidth =
            measurables
                .fastFirstOrNull { it.layoutId == TrailingId }
                ?.let { intrinsicMeasurer(it, height) } ?: 0
        val leadingWidth =
            measurables
                .fastFirstOrNull { it.layoutId == LeadingId }
                ?.let { intrinsicMeasurer(it, height) } ?: 0
        val prefixWidth =
            measurables
                .fastFirstOrNull { it.layoutId == PrefixId }
                ?.let { intrinsicMeasurer(it, height) } ?: 0
        val suffixWidth =
            measurables
                .fastFirstOrNull { it.layoutId == SuffixId }
                ?.let { intrinsicMeasurer(it, height) } ?: 0
        val placeholderWidth =
            measurables
                .fastFirstOrNull { it.layoutId == PlaceholderId }
                ?.let { intrinsicMeasurer(it, height) } ?: 0
        return calculateWidth(
            leadingPlaceableWidth = leadingWidth,
            trailingPlaceableWidth = trailingWidth,
            prefixPlaceableWidth = prefixWidth,
            suffixPlaceableWidth = suffixWidth,
            textFieldPlaceableWidth = textFieldWidth,
            labelPlaceableWidth = labelWidth,
            placeholderPlaceableWidth = placeholderWidth,
            constraints = Constraints(),
            labelProgress = labelProgress(),
        )
    }

    private fun IntrinsicMeasureScope.intrinsicHeight(
        measurables: List<IntrinsicMeasurable>,
        width: Int,
        intrinsicMeasurer: (IntrinsicMeasurable, Int) -> Int,
    ): Int {
        val labelProgress = labelProgress()
        var remainingWidth = width
        val leadingHeight =
            measurables
                .fastFirstOrNull { it.layoutId == LeadingId }
                ?.let {
                    remainingWidth =
                        remainingWidth.subtractConstraintSafely(
                            it.maxIntrinsicWidth(Constraints.Infinity)
                        )
                    intrinsicMeasurer(it, width)
                } ?: 0
        val trailingHeight =
            measurables
                .fastFirstOrNull { it.layoutId == TrailingId }
                ?.let {
                    remainingWidth =
                        remainingWidth.subtractConstraintSafely(
                            it.maxIntrinsicWidth(Constraints.Infinity)
                        )
                    intrinsicMeasurer(it, width)
                } ?: 0

        val labelHeight =
            measurables
                .fastFirstOrNull { it.layoutId == LabelId }
                ?.let { intrinsicMeasurer(it, lerp(remainingWidth, width, labelProgress)) } ?: 0

        val prefixHeight =
            measurables
                .fastFirstOrNull { it.layoutId == PrefixId }
                ?.let {
                    val height = intrinsicMeasurer(it, remainingWidth)
                    remainingWidth =
                        remainingWidth.subtractConstraintSafely(
                            it.maxIntrinsicWidth(Constraints.Infinity)
                        )
                    height
                } ?: 0
        val suffixHeight =
            measurables
                .fastFirstOrNull { it.layoutId == SuffixId }
                ?.let {
                    val height = intrinsicMeasurer(it, remainingWidth)
                    remainingWidth =
                        remainingWidth.subtractConstraintSafely(
                            it.maxIntrinsicWidth(Constraints.Infinity)
                        )
                    height
                } ?: 0

        val textFieldHeight =
            intrinsicMeasurer(measurables.fastFirst { it.layoutId == TextFieldId }, remainingWidth)

        val placeholderHeight =
            measurables
                .fastFirstOrNull { it.layoutId == PlaceholderId }
                ?.let { intrinsicMeasurer(it, remainingWidth) } ?: 0

        val supportingHeight =
            measurables
                .fastFirstOrNull { it.layoutId == SupportingId }
                ?.let { intrinsicMeasurer(it, width) } ?: 0

        return calculateHeight(
            leadingHeight = leadingHeight,
            trailingHeight = trailingHeight,
            prefixHeight = prefixHeight,
            suffixHeight = suffixHeight,
            textFieldHeight = textFieldHeight,
            labelHeight = labelHeight,
            placeholderHeight = placeholderHeight,
            supportingHeight = supportingHeight,
            constraints = Constraints(),
            isLabelAbove = labelPosition is TextFieldLabelPosition.Above,
            labelProgress = labelProgress,
        )
    }

    /**
     * Calculate the width of the [OutlinedTextField] given all elements that should be placed
     * inside.
     */
    private fun Density.calculateWidth(
        leadingPlaceableWidth: Int,
        trailingPlaceableWidth: Int,
        prefixPlaceableWidth: Int,
        suffixPlaceableWidth: Int,
        textFieldPlaceableWidth: Int,
        labelPlaceableWidth: Int,
        placeholderPlaceableWidth: Int,
        constraints: Constraints,
        labelProgress: Float,
    ): Int {
        val affixTotalWidth = prefixPlaceableWidth + suffixPlaceableWidth
        val middleSection =
            maxOf(
                textFieldPlaceableWidth + affixTotalWidth,
                placeholderPlaceableWidth + affixTotalWidth,
                // Prefix/suffix does not get applied to label
                lerp(labelPlaceableWidth, 0, labelProgress),
            )
        val wrappedWidth = leadingPlaceableWidth + middleSection + trailingPlaceableWidth

        // Actual LayoutDirection doesn't matter; we only need the sum
        val labelHorizontalPadding =
            (paddingValues.calculateLeftPadding(LayoutDirection.Ltr) +
                    paddingValues.calculateRightPadding(LayoutDirection.Ltr))
                .toPx()
        val focusedLabelWidth =
            ((labelPlaceableWidth + labelHorizontalPadding) * labelProgress).roundToInt()
        return constraints.constrainWidth(max(wrappedWidth, focusedLabelWidth))
    }

    /**
     * Calculate the height of the [OutlinedTextField] given all elements that should be placed
     * inside. This includes the supporting text, if it exists, even though this element is not
     * "visually" inside the text field.
     */
    private fun Density.calculateHeight(
        leadingHeight: Int,
        trailingHeight: Int,
        prefixHeight: Int,
        suffixHeight: Int,
        textFieldHeight: Int,
        labelHeight: Int,
        placeholderHeight: Int,
        supportingHeight: Int,
        constraints: Constraints,
        isLabelAbove: Boolean,
        labelProgress: Float,
    ): Int {
        val inputFieldHeight =
            maxOf(
                textFieldHeight,
                placeholderHeight,
                prefixHeight,
                suffixHeight,
                if (isLabelAbove) 0 else lerp(labelHeight, 0, labelProgress),
            )
        val topPadding = paddingValues.calculateTopPadding().toPx()
        val actualTopPadding =
            if (isLabelAbove) {
                topPadding
            } else {
                lerp(topPadding, max(topPadding, labelHeight / 2f), labelProgress)
            }
        val bottomPadding = paddingValues.calculateBottomPadding().toPx()
        val middleSectionHeight = actualTopPadding + inputFieldHeight + bottomPadding

        return constraints.constrainHeight(
            (if (isLabelAbove) labelHeight else 0) +
                    maxOf(leadingHeight, trailingHeight, middleSectionHeight.roundToInt()) +
                    supportingHeight
        )
    }

    /**
     * Places the provided text field, placeholder, label, optional leading and trailing icons
     * inside the [OutlinedTextField]
     */
    private fun Placeable.PlacementScope.place(
        totalHeight: Int,
        width: Int,
        leadingPlaceable: Placeable?,
        trailingPlaceable: Placeable?,
        prefixPlaceable: Placeable?,
        suffixPlaceable: Placeable?,
        textFieldPlaceable: Placeable,
        labelPlaceable: Placeable?,
        placeholderPlaceable: Placeable?,
        containerPlaceable: Placeable,
        supportingPlaceable: Placeable?,
        density: Float,
        layoutDirection: LayoutDirection,
        isLabelAbove: Boolean,
        labelProgress: Float,
        iconPadding: Float,
    ) {
        val yOffset = if (isLabelAbove) labelPlaceable.heightOrZero else 0

        // place container
        containerPlaceable.place(0, yOffset)

        // Most elements should be positioned w.r.t the text field's "visual" height, i.e.,
        // excluding the label (if it's Above) and the supporting text on bottom
        val height =
            totalHeight -
                    supportingPlaceable.heightOrZero -
                    (if (isLabelAbove) labelPlaceable.heightOrZero else 0)

        val topPadding = (paddingValues.calculateTopPadding().value * density).roundToInt()

        // placed center vertically and to the start edge horizontally
        leadingPlaceable?.placeRelative(
            0,
            yOffset + Alignment.CenterVertically.align(leadingPlaceable.height, height),
        )

        // label position is animated
        // in single line text field, label is centered vertically before animation starts
        labelPlaceable?.let {
            val startY =
                when {
                    isLabelAbove -> 0
                    singleLine -> Alignment.CenterVertically.align(it.height, height)
                    else -> topPadding
                }
            val endY =
                when {
                    isLabelAbove -> 0
                    else -> -(it.height / 2)
                }
            val positionY = lerp(startY, endY, labelProgress)

            if (isLabelAbove) {
                val positionX =
                    labelPosition.minimizedAlignment.align(
                        size = labelPlaceable.width,
                        space = width,
                        layoutDirection = layoutDirection,
                    )
                // Not placeRelative because alignment already handles RTL
                labelPlaceable.place(positionX, positionY)
            } else {
                val startPadding =
                    paddingValues.calculateStartPadding(layoutDirection).value * density
                val endPadding = paddingValues.calculateEndPadding(layoutDirection).value * density
                val leadingPlusPadding =
                    if (leadingPlaceable == null) {
                        startPadding
                    } else {
                        leadingPlaceable.width + (startPadding - iconPadding).coerceAtLeast(0f)
                    }
                val trailingPlusPadding =
                    if (trailingPlaceable == null) {
                        endPadding
                    } else {
                        trailingPlaceable.width + (endPadding - iconPadding).coerceAtLeast(0f)
                    }
                val leftPadding =
                    if (layoutDirection == LayoutDirection.Ltr) startPadding else endPadding
                val leftIconPlusPadding =
                    if (layoutDirection == LayoutDirection.Ltr) leadingPlusPadding
                    else trailingPlusPadding
                val startX =
                    labelPosition.expandedAlignment.align(
                        size = labelPlaceable.width,
                        space = width - (leadingPlusPadding + trailingPlusPadding).roundToInt(),
                        layoutDirection = layoutDirection,
                    ) + leftIconPlusPadding

                val endX =
                    labelPosition.minimizedAlignment.align(
                        size = labelPlaceable.width,
                        space = width - (startPadding + endPadding).roundToInt(),
                        layoutDirection = layoutDirection,
                    ) + leftPadding
                val positionX = lerp(startX, endX, labelProgress).roundToInt()
                // Not placeRelative because alignment already handles RTL
                labelPlaceable.place(positionX, positionY)
            }
        }

        fun calculateVerticalPosition(placeable: Placeable): Int {
            val defaultPosition =
                yOffset +
                        if (singleLine) {
                            // Single line text fields have text components centered vertically.
                            Alignment.CenterVertically.align(placeable.height, height)
                        } else {
                            // Multiline text fields have text components aligned to top with padding.
                            topPadding
                        }
            return if (labelPosition is TextFieldLabelPosition.Above) {
                defaultPosition
            } else {
                // Ensure components are placed below label when it's in the border
                max(defaultPosition, labelPlaceable.heightOrZero / 2)
            }
        }

        prefixPlaceable?.placeRelative(
            leadingPlaceable.widthOrZero,
            calculateVerticalPosition(prefixPlaceable),
        )

        val textHorizontalPosition = leadingPlaceable.widthOrZero + prefixPlaceable.widthOrZero

        textFieldPlaceable.placeRelative(
            textHorizontalPosition,
            calculateVerticalPosition(textFieldPlaceable),
        )

        // placed similar to the input text above
        placeholderPlaceable?.placeRelative(
            textHorizontalPosition,
            calculateVerticalPosition(placeholderPlaceable),
        )

        suffixPlaceable?.placeRelative(
            width - trailingPlaceable.widthOrZero - suffixPlaceable.width,
            calculateVerticalPosition(suffixPlaceable),
        )

        // placed center vertically and to the end edge horizontally
        trailingPlaceable?.placeRelative(
            width - trailingPlaceable.width,
            yOffset + Alignment.CenterVertically.align(trailingPlaceable.height, height),
        )

        // place supporting text
        supportingPlaceable?.placeRelative(0, yOffset + height)
    }
}

internal fun Modifier.outlineCutout(
    labelSize: () -> Size,
    alignment: Alignment.Horizontal,
    paddingValues: PaddingValues,
) =
    this.drawWithContent {
        val labelSizeValue = labelSize()
        val labelWidth = labelSizeValue.width
        if (labelWidth > 0f) {
            val innerPadding = OutlinedTextFieldInnerPadding.toPx()
            val leftPadding = paddingValues.calculateLeftPadding(layoutDirection).toPx()
            val rightPadding = paddingValues.calculateRightPadding(layoutDirection).toPx()
            val labelCenter =
                alignment.align(
                    size = labelWidth.roundToInt(),
                    space = (size.width - leftPadding - rightPadding).roundToInt(),
                    layoutDirection = layoutDirection,
                ) + leftPadding + (labelWidth / 2)
            val left = (labelCenter - (labelWidth / 2) - innerPadding).coerceAtLeast(0f)
            val right = (labelCenter + (labelWidth / 2) + innerPadding).coerceAtMost(size.width)
            val labelHeight = labelSizeValue.height
            // using label height as a cutout area to make sure that no hairline artifacts are
            // left when we clip the border
            clipRect(left, -labelHeight / 2, right, labelHeight / 2, ClipOp.Difference) {
                this@drawWithContent.drawContent()
            }
        } else {
            this@drawWithContent.drawContent()
        }
    }

private val OutlinedTextFieldInnerPadding = 4.dp