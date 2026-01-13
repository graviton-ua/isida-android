package com.whoppah.common.compose.transitions

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import kotlin.math.roundToInt

object SlideInOutTransition {
    inline fun <reified T> AnimatedContentTransitionScope<T>.enterTransition(): EnterTransition {
        return slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(durationMillis = DURATION, delayMillis = DELAY),
            initialOffset = { (it * 0.2f).roundToInt() },
        ) + fadeIn(animationSpec = tween(durationMillis = DURATION, delayMillis = DELAY))
    }

    inline fun <reified T> AnimatedContentTransitionScope<T>.exitTransition(): ExitTransition {
        return slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(durationMillis = DURATION, delayMillis = DELAY),
            targetOffset = { (it * 0.2f).roundToInt() },
        )
    }

    inline fun <reified T> AnimatedContentTransitionScope<T>.popEnterTransition(): EnterTransition {
        return slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tween(durationMillis = DURATION, delayMillis = DELAY),
            initialOffset = { (it * 0.2f).roundToInt() },
        )
    }

    inline fun <reified T> AnimatedContentTransitionScope<T>.popExitTransition(): ExitTransition {
        return slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tween(durationMillis = DURATION, delayMillis = DELAY),
            targetOffset = { (it * 0.2f).roundToInt() },
        ) + fadeOut(animationSpec = tween(durationMillis = DURATION, delayMillis = DELAY))
    }


    const val DURATION = 220
    const val DELAY = 80
}