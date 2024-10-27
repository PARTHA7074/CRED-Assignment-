package com.partha.credassignment.ui.composableComponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StackView(
    modifier: Modifier = Modifier,
    showSliders: List<Boolean>,
    contents: List<@Composable () -> Unit>
) {
    Box(modifier = modifier.padding(top = 50.dp)) {
        contents.forEachIndexed { index, content ->
            if (index == 0) {
                content()
            } else {
                AnimatedVisibility(
                    visible = showSliders.getOrNull(index - 1) == true,
                    enter = slideInVertically(initialOffsetY = { it * index }),
                    exit = slideOutVertically(targetOffsetY = { it * index })
                ) {
                    Box(modifier = Modifier.padding(top = 80.dp * index)) {
                        content()
                    }
                }
            }
        }
    }
}