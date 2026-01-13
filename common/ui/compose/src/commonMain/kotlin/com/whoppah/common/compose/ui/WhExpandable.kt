package com.whoppah.common.compose.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun WhExpandable(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    initExpanded: Boolean = false,
    header: @Composable ColumnScope.() -> Unit,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    Column(modifier = modifier) {
        header()
        WhExpandable(
            expanded = expanded,
            initExpanded = initExpanded,
            modifier = Modifier.fillMaxWidth(),
            content = content
        )
    }
}

@Composable
fun WhExpandable(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    initExpanded: Boolean = false,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    val visibleState = remember { MutableTransitionState(initExpanded) }.apply { targetState = expanded }
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = FADE_IN_ANIMATION_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember { expandVertically(animationSpec = tween(EXPAND_ANIMATION_DURATION)) }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = FADE_OUT_ANIMATION_DURATION,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember { shrinkVertically(animationSpec = tween(COLLAPSE_ANIMATION_DURATION)) }
    AnimatedVisibility(
        modifier = modifier,
        visibleState = visibleState,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut,
        content = content,
    )
}

private const val FADE_IN_ANIMATION_DURATION = 300
private const val EXPAND_ANIMATION_DURATION = 300
private const val FADE_OUT_ANIMATION_DURATION = 300
private const val COLLAPSE_ANIMATION_DURATION = 300