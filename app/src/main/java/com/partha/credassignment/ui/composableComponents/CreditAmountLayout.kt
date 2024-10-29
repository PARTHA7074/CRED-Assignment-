package com.partha.credassignment.ui.composableComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
            .clickable(enabled = isStackedBehind) { onClick() }
            .padding(horizontal = 25.dp, vertical = 16.dp)
    ) {
        if (isStackedBehind){
            StackedBehindCreditAmountSlideHeader(
                modifier = Modifier.align(Alignment.TopStart),
                creditAmount = creditAmount
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                SlideHeader(
                    modifier = Modifier.padding(vertical = 10.dp),
                    heading = "nikunj, how much do you need?",
                    subHeading = "move the dial and set any amount you need up to ₹$maxCreditAmount"
                )

                Box(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    CircularDraggableProgressBar(
                        modifier = Modifier
                            .padding(top = 32.dp, bottom = 64.dp)
                            .size(240.dp)
                            .align(Alignment.Center),
                        initialProgress = creditAmount.toFloat()/maxCreditAmount,
                        onProgressChange = { newProgress ->
                            creditAmount = (newProgress * maxCreditAmount).toInt()
                        }
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
fun StackedBehindCreditAmountSlideHeader(
    modifier: Modifier = Modifier,
    creditAmount: Int = 0
){
    Row(modifier = modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StackedBehindHeaderContent(
            heading = "credit amount",
            subHeading = "₹$creditAmount"
        )

        DropDownArrow()
    }
}

@Composable
@Preview
fun CreditAmountSlidePreview() {
    CreditAmountSlide()
}