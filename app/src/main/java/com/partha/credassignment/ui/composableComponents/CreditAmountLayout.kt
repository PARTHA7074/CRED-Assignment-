package com.partha.credassignment.ui.composableComponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CreditAmountSlide(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF14191d),
    onClick: () -> Unit = {},
    isStackedBehind: Boolean = false
) {
    var creditAmount by remember { mutableIntStateOf(350000) }
    val maxCreditAmount = 500000
    val monthlyRate = 1.04f

    Box(
        modifier = modifier
            .fillMaxSize()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .background(color, shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 25.dp, vertical = 16.dp)
    ) {
        if (isStackedBehind){
            CreditAmountSlideHeader(
                modifier = Modifier.align(Alignment.TopStart),
                creditAmount = creditAmount
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            )  {
                Text(
                    text = "nikunj, how much do you need?",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "move the dial and set any amount you need up to ₹$maxCreditAmount",
                    color = Color(0xFFBDBDBD),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    CircularProgressWithSolidColor(
                        modifier = Modifier
                            .padding(top = 32.dp, bottom = 64.dp)
                            .size(240.dp)
                            .align(Alignment.Center),
                        progress = creditAmount.toFloat()/maxCreditAmount
                    )
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "credit amount", color = Color.DarkGray, fontSize = 16.sp)
                        Text(
                            text = "₹$creditAmount",
                            color = Color.Black,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(text = "@$monthlyRate% monthly", color = Color.Green, fontSize = 14.sp)
                    }

                    Text(
                        text = "stash is instant. money will be credited within seconds",
                        color = Color.Black,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }

    }
}

@Composable
fun CircularProgressWithSolidColor(
    modifier: Modifier = Modifier,
    progress: Float,
    progressColor: Color = Color(0xFFD9735B),
    backGroundCircleColor: Color = Color(0xFFfee7dd)
) {
    Canvas(modifier = modifier) {
        val strokeWidth = 12.dp.toPx()
        val radius = size.minDimension / 2 - strokeWidth / 2

        val startAngle = -90f
        val sweepAngle = 360f * progress

        // Draw background circle
        drawCircle(
            color = backGroundCircleColor,
            radius = radius,
            style = Stroke(width = strokeWidth)
        )

        // Draw solid color progress
        drawArc(
            color = progressColor,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset((size.width - radius * 2) / 2, (size.height - radius * 2) / 2)
        )

        // Small black circular button on the ring
        drawCircle(
            color = Color.Black,
            radius = 8.dp.toPx(),
            center = Offset(
                x = center.x + radius * cos(Math.toRadians((startAngle + sweepAngle).toDouble())).toFloat(),
                y = center.y + radius * sin(Math.toRadians((startAngle + sweepAngle).toDouble())).toFloat()
            )
        )
    }
}

@Composable
fun CreditAmountSlideHeader(
    modifier: Modifier = Modifier,
    creditAmount: Int = 0
){
    val textColor = Color.DarkGray
    val iconColor = Color(0xFFB0B0B0)

    Row(modifier = modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(
                text = "credit amount",
                color = textColor,
                fontSize = 12.sp
            )
            Text(
                text = "₹$creditAmount",
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "Dropdown Arrow",
            tint = iconColor
        )
    }

}


@Composable
@Preview
fun CreditAmountSlidePreview() {
    CreditAmountSlide()
}