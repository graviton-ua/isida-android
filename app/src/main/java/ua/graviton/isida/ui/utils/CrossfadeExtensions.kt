package ua.graviton.isida.ui.utils

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Suppress("UpdateTransitionLabel")
@Composable
fun <T> Crossfade(
    targetState: T,
    modifier: Modifier = Modifier,
    contentKey: (targetState: T) -> Any? = { it },
    animationSpec: FiniteAnimationSpec<Float> = tween(),
    content: @Composable (T) -> Unit
) {
    val transition = updateTransition(targetState)
    transition.Crossfade(modifier, animationSpec, contentKey = contentKey, content = content)
}