package com.whoppah.common.compose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.whoppah.common.compose.theme.WhoppahTheme

/**
 * A custom Scaffold implementation that provides a consistent layout structure for screens.
 *
 * This composable arranges common UI elements like top bars, bottom bars, and floating action
 * buttons, while automatically handling window insets. It also provides the top app bar's
 * height to its content via [LocalWhScaffoldTopAppBarHeight].
 *
 * @param modifier The [Modifier] to be applied to the scaffold.
 * @param topBar The top app bar of the screen.
 * @param bottomBar The bottom navigation bar of the screen.
 * @param snackbarHost The component responsible for showing snackbars.
 * @param floatingActionButton The main floating action button for the screen.
 * @param floatingActionButtonPosition The position of the floating action button.
 * @param backgroundColor The background color for the scaffold's surface.
 * @param contentColor The preferred content color for the scaffold's surface.
 * @param contentWindowInsets The window insets to be applied to the content area.
 * @param isContentBehindTopBar If `true`, the main content will be drawn behind the `topBar`.
 * The `content` composable is responsible for using the provided padding to avoid being
 * obscured by the `topBar`.
 * @param content The main screen content. The `PaddingValues` provided contain the space
 * occupied by the top and bottom bars.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WhScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    backgroundColor: Color = WhoppahTheme.colors.background,
    contentColor: Color = contentColorFor(backgroundColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    isContentBehindTopBar: Boolean = false,
    content: @Composable (PaddingValues) -> Unit
) {
    val safeInsets = remember(contentWindowInsets) { MutableWindowInsets(contentWindowInsets) }
    Surface(
        modifier = modifier.onConsumedWindowInsetsChanged { consumedWindowInsets ->
            // Exclude currently consumed window insets from user provided contentWindowInsets
            safeInsets.insets = contentWindowInsets.exclude(consumedWindowInsets)
        },
        color = backgroundColor,
        contentColor = contentColor,
    ) {
        ScaffoldLayout(
            isContentBehindTopBar = isContentBehindTopBar,
            fabPosition = floatingActionButtonPosition,
            topBar = topBar,
            bottomBar = bottomBar,
            content = content,
            snackbar = snackbarHost,
            contentWindowInsets = safeInsets,
            fab = floatingActionButton,
        )
    }
}

/**
 * The private layout logic for [WhScaffold].
 *
 * This composable measures and places the various scaffold components (top bar, content,
 * FAB, etc.) using a [SubcomposeLayout].
 *
 * @param isContentBehindTopBar Determines if the main content should be laid out behind the top bar.
 * @param fabPosition The [FabPosition] for the floating action button.
 * @param topBar The top bar composable.
 * @param content The main body content composable.
 * @param snackbar The snackbar composable.
 * @param fab The floating action button composable.
 * @param contentWindowInsets The window insets to be respected by the layout.
 * @param bottomBar The bottom bar composable.
 */
@Composable
private fun ScaffoldLayout(
    isContentBehindTopBar: Boolean,
    fabPosition: FabPosition,
    topBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    snackbar: @Composable () -> Unit,
    fab: @Composable () -> Unit,
    contentWindowInsets: WindowInsets,
    bottomBar: @Composable () -> Unit
) {
    // Create the backing value for the content padding
    // These values will be updated during measurement, but before subcomposing the body content
    // Remembering and updating a single PaddingValues avoids needing to recompose when the values
    // change
    val contentPadding = remember {
        object : PaddingValues {
            var paddingHolder by mutableStateOf(PaddingValues(0.dp))

            override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp =
                paddingHolder.calculateLeftPadding(layoutDirection)

            override fun calculateTopPadding(): Dp = paddingHolder.calculateTopPadding()

            override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp =
                paddingHolder.calculateRightPadding(layoutDirection)

            override fun calculateBottomPadding(): Dp = paddingHolder.calculateBottomPadding()
        }
    }
    val appbarHeightState = remember { mutableStateOf<Dp?>(null) }

    val topBarContent: @Composable () -> Unit = remember(topBar) { { Box { topBar() } } }
    val snackbarContent: @Composable () -> Unit = remember(snackbar) { { Box { snackbar() } } }
    val fabContent: @Composable () -> Unit = remember(fab) { { Box { fab() } } }
    val bodyContent: @Composable () -> Unit = remember(content, contentPadding) {
        {
            Box {
                CompositionLocalProvider(
                    LocalWhScaffoldTopAppBarHeight provides appbarHeightState.value
                ) {
                    content(contentPadding)
                }
            }
        }
    }
    val bottomBarContent: @Composable () -> Unit = remember(bottomBar) { { Box { bottomBar() } } }
    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        // respect only bottom and horizontal for snackbar and fab
        val leftInset = contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection)
        val rightInset = contentWindowInsets.getRight(this@SubcomposeLayout, layoutDirection)
        val bottomInset = contentWindowInsets.getBottom(this@SubcomposeLayout)

        val topBarPlaceable = subcompose(ScaffoldLayoutContent.TopBar, topBarContent).first().measure(looseConstraints)

        val snackbarPlaceable = subcompose(ScaffoldLayoutContent.Snackbar, snackbarContent).first()
            .measure(looseConstraints.offset(-leftInset - rightInset, -bottomInset))

        val fabPlaceable = subcompose(ScaffoldLayoutContent.Fab, fabContent).first()
            .measure(looseConstraints.offset(-leftInset - rightInset, -bottomInset))

        val isFabEmpty = fabPlaceable.width == 0 && fabPlaceable.height == 0
        val fabPlacement =
            if (!isFabEmpty) {
                val fabWidth = fabPlaceable.width
                val fabHeight = fabPlaceable.height
                // FAB distance from the left of the layout, taking into account LTR / RTL
                val fabLeftOffset =
                    when (fabPosition) {
                        FabPosition.Start -> {
                            if (layoutDirection == LayoutDirection.Ltr) {
                                FabSpacing.roundToPx() + leftInset
                            } else {
                                layoutWidth - FabSpacing.roundToPx() - fabWidth - rightInset
                            }
                        }

                        FabPosition.End,
                        FabPosition.EndOverlay -> {
                            if (layoutDirection == LayoutDirection.Ltr) {
                                layoutWidth - FabSpacing.roundToPx() - fabWidth - rightInset
                            } else {
                                FabSpacing.roundToPx() + leftInset
                            }
                        }

                        else -> (layoutWidth - fabWidth + leftInset - rightInset) / 2
                    }

                FabPlacement(left = fabLeftOffset, width = fabWidth, height = fabHeight)
            } else {
                null
            }

        val bottomBarPlaceable = subcompose(ScaffoldLayoutContent.BottomBar, bottomBarContent).first().measure(looseConstraints)

        val isBottomBarEmpty = bottomBarPlaceable.width == 0 && bottomBarPlaceable.height == 0

        val fabOffsetFromBottom =
            fabPlacement?.let {
                if (isBottomBarEmpty || fabPosition == FabPosition.EndOverlay) {
                    it.height +
                            FabSpacing.roundToPx() +
                            contentWindowInsets.getBottom(this@SubcomposeLayout)
                } else {
                    // Total height is the bottom bar height + the FAB height + the padding
                    // between the FAB and bottom bar
                    bottomBarPlaceable.height + it.height + FabSpacing.roundToPx()
                }
            }

        val snackbarHeight = snackbarPlaceable.height
        val snackbarOffsetFromBottom =
            if (snackbarHeight != 0) {
                snackbarHeight +
                        (fabOffsetFromBottom
                            ?: bottomBarPlaceable.height.takeIf { !isBottomBarEmpty }
                            ?: contentWindowInsets.getBottom(this@SubcomposeLayout))
            } else 0

        // Update the backing state for the content padding before subcomposing the body
        val insets = contentWindowInsets.asPaddingValues(this)
        contentPadding.paddingHolder = PaddingValues(
            top = if (topBarPlaceable.width == 0 && topBarPlaceable.height == 0 || isContentBehindTopBar) insets.calculateTopPadding() else topBarPlaceable.height.toDp(),
            bottom = if (isBottomBarEmpty) insets.calculateBottomPadding() else bottomBarPlaceable.height.toDp(),
            start = insets.calculateStartPadding(layoutDirection),
            end = insets.calculateEndPadding(layoutDirection),
        )
        appbarHeightState.value = topBarPlaceable.height.toDp()

        val bodyContentPlaceable = subcompose(ScaffoldLayoutContent.MainContent, bodyContent).first().measure(looseConstraints)

        layout(layoutWidth, layoutHeight) {
            // Placing to control drawing order to match default elevation of each placeable
            bodyContentPlaceable.place(0, 0)
            topBarPlaceable.place(0, 0)
            snackbarPlaceable.place(
                (layoutWidth - snackbarPlaceable.width +
                        contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection) -
                        contentWindowInsets.getRight(this@SubcomposeLayout, layoutDirection)) / 2,
                layoutHeight - snackbarOffsetFromBottom,
            )
            // The bottom bar is always at the bottom of the layout
            bottomBarPlaceable.place(0, layoutHeight - (bottomBarPlaceable.height))
            // Explicitly not using placeRelative here as `leftOffset` already accounts for RTL
            fabPlacement?.let { placement ->
                fabPlaceable.place(placement.left, layoutHeight - fabOffsetFromBottom!!)
            }
        }
    }
}

/**
 * An immutable data class holding placement information for a [FloatingActionButton].
 *
 * @property left The FAB's calculated offset from the left edge, accounting for layout direction.
 * @property width The measured width of the FAB.
 * @property height The measured height of the FAB.
 */
@Immutable internal class FabPlacement(val left: Int, val width: Int, val height: Int)

// Default spacing for the FAB from the scaffold edges.
private val FabSpacing = 16.dp

// Used by SubcomposeLayout to identify the different scaffold slots.
private enum class ScaffoldLayoutContent { TopBar, MainContent, Snackbar, Fab, BottomBar }

/**
 * Provides the measured height of the top app bar within a [WhScaffold].
 *
 * This `CompositionLocal` is intended for composables within the scaffold's `content` slot.
 * It allows them to query the app bar's height, which is essential for applying correct
 * padding or offsets, especially when content is drawn behind the top app bar
 * (i.e., when `isContentBehindTopBar` is true).
 *
 * The value is a nullable `Dp` (`Dp?`). It will be `null` if the top app bar is not
 * present in the scaffold.
 *
 * A `CompositionLocal` is used here as an efficient alternative to an `onSizeChanged`
 * modifier. It avoids potential redundant recompositions, as `onSizeChanged` does not
 * guarantee it will be invoked only when the size actually changes.
 */
val LocalWhScaffoldTopAppBarHeight = compositionLocalOf<Dp?> { null }