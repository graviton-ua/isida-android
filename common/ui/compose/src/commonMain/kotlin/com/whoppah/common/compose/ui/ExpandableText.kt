package com.whoppah.common.compose.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun ExpandingText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    state: ExpandableTextState = rememberExpandableTextState(),
) {
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }

    val textLayoutResult = textLayoutResultState.value
    LaunchedEffect(textLayoutResult, text, state) {
        if (textLayoutResult == null) return@LaunchedEffect

        if (!state.isExpanded) state.setExpandable(textLayoutResult.hasVisualOverflow)
    }

    Text(
        text = text,
        overflow = TextOverflow.Ellipsis,
        maxLines = if (state.isExpanded) Int.MAX_VALUE else state.minimizedMaxLines,
        onTextLayout = { textLayoutResultState.value = it },
        modifier = modifier.animateContentSize(),
    )
}

@Composable
fun rememberExpandableTextState(
    initExpand: Boolean = false,
    minimizedMaxLines: Int = 2,
): ExpandableTextState {
    return remember {
        ExpandableTextState(
            initExpand = initExpand,
            minimizedMaxLines = minimizedMaxLines,
        )
    }
}

@Stable
class ExpandableTextState(
    initExpand: Boolean = false,
    val minimizedMaxLines: Int = 2,
) {
    private val expandedState = mutableStateOf(initExpand)
    private val expandableState = mutableStateOf(false)

    val isExpanded: Boolean get() = expandedState.value
    val isExpandable: Boolean get() = expandableState.value

    fun expand() = with(expandedState) { value = true }
    fun collapse() = with(expandedState) { value = false }

    internal fun setExpandable(expandable: Boolean) = with(expandableState) { value = expandable }
}