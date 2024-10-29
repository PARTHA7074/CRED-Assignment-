package com.partha.credassignment.ui.composableComponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.partha.credassignment.ui.theme.CREDAssignmentTheme
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularDraggableProgressBar(
    modifier: Modifier = Modifier,
    initialProgress: Float = 0f,
    onProgressChange: (Float) -> Unit = {},
    progressColor: Color = Color(0xFFD9735B),
    backGroundCircleColor: Color = Color(0xFFfee7dd),
    strokeWidth: Dp = 12.dp
) {
    var progress by rememberSaveable { mutableFloatStateOf(initialProgress) }

    Canvas(
        modifier = modifier
            .defaultMinSize(minWidth = 220.dp, minHeight = 220.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    change.consume()

                    // Calculate the center of the canvas
                    val canvasCenter = Offset(size.width / 2f, size.height / 2f)
                    val touchPosition = change.position
                    val angle = calculateAngleFromCenter(touchPosition, canvasCenter)

                    // Convert angle to progress (0f - 1f range)
                    val newProgress = (angle / 360f).coerceIn(0f, 1f)
                    
                    if (newProgress != progress) {
                        progress = newProgress
                        onProgressChange(newProgress)
                    }
                }
            }
    ) {
        val strokeWidthPx = strokeWidth.toPx()
        val radius = minOf(size.width, size.height) / 2 - strokeWidthPx / 2

        val startAngle = -90f
        val sweepAngle = 360f * progress

        // Calculate the center of the canvas
        val canvasCenter = Offset(size.width / 2, size.height / 2)

        // Draw background circle
        drawCircle(
            color = backGroundCircleColor,
            radius = radius,
            center = canvasCenter,
            style = Stroke(width = strokeWidthPx)
        )

        // Draw solid color progress
        drawArc(
            color = progressColor,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset((size.width - radius * 2) / 2, (size.height - radius * 2) / 2)
        )

        // Small black circular button on the ring
        drawCircle(
            color = Color.Black,
            radius = 8.dp.toPx(),
            center = Offset(
                x = canvasCenter.x + radius * cos(Math.toRadians((startAngle + sweepAngle).toDouble())).toFloat(),
                y = canvasCenter.y + radius * sin(Math.toRadians((startAngle + sweepAngle).toDouble())).toFloat()
            )
        )
    }
}

fun calculateAngleFromCenter(touchPosition: Offset, center: Offset): Float {
    val angle = Math.toDegrees(
        atan2(
            (touchPosition.y - center.y).toDouble(),
            (touchPosition.x - center.x).toDouble()
        )
    ).toFloat()

    // Convert the angle to start from the top (-90 degrees) and make it positive
    return (angle + 90f).let { if (it < 0) it + 360 else it }
}

@Preview
@Composable
fun CircularDraggableProgressBarPreview() {
    CREDAssignmentTheme {
        CircularDraggableProgressBar(
            initialProgress = 0.5f
        )
    }
}