package com.partha.credassignment.ui.composableComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.partha.credassignment.ui.activities.showNotImplementedToast

@Composable
fun EMISelectionSlide(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF1a1927),
    onClick: () -> Unit = {},
    isStackedBehind: Boolean = false
) {
    val context = LocalContext.current
    var selectedIndex by remember { mutableIntStateOf(-1) }
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
            .padding(vertical = 16.dp)
    ) {
        if (isStackedBehind) {
            StackedBehindEMISelectionSlideHeader(
                modifier = Modifier.padding(horizontal = 25.dp),
                emiAmount = cardItems[selectedIndex].pricePerMonth,
                emiDuration = cardItems[selectedIndex].duration
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                SlideHeader(
                    modifier = Modifier.padding(horizontal = 25.dp),
                    heading = "how do you wish to repay?",
                    subHeading = "choose one of our recommended plan or make your own"
                )

                LazyRow {
                    item {
                        Spacer(modifier = Modifier.padding(start = 25.dp))
                    }
                    itemsIndexed(cardItems) { index, item ->
                        EMIOptionItem(
                            modifier = Modifier
                                .clickable {
                                    selectedIndex = index
                                },
                            data = item,
                            isSelected = if (selectedIndex != -1) selectedIndex == index
                            else {
                                if (item.isRecommended) selectedIndex = index
                                item.isRecommended
                            }
                        )
                    }
                }

                OutlinedButton(
                    modifier = Modifier.padding(start = 25.dp, top = 30.dp),
                    onClick = { showNotImplementedToast(context) }
                ) {
                    Text(
                        text = "Create your own plan",
                        color = Color.White.copy(alpha = .5f)
                    )
                }

            }
        }
    }
}

@Composable
fun StackedBehindEMISelectionSlideHeader(
    modifier: Modifier = Modifier,
    emiAmount: String = "₹5,580 /mo",
    emiDuration: String = "9 months"
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StackedBehindHeaderContent(
            heading = "EMI",
            subHeading = emiAmount
        )

        StackedBehindHeaderContent(
            heading = "duration",
            subHeading = emiDuration
        )

        DropDownArrow()
    }
}


@Composable
fun EMIOptionItem(data: CardData, modifier: Modifier = Modifier, isSelected: Boolean = false) {

    Box(
        modifier = modifier
            .padding(top = 26.dp, end = 16.dp)
            .background(
                shape = RoundedCornerShape(20.dp),
                color = data.backgroundColor
            )
            .padding(8.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        // Recommended Label
        if (data.isRecommended) {
            Text(
                modifier = Modifier
                    .offset(y = (-23).dp)
                    .background(Color(0xFFE3E0E9), shape = RoundedCornerShape(20.dp))
                    .padding(vertical = 5.dp, horizontal = 10.dp),
                text = "recommended",
                fontSize = 14.sp,
                color = Color(0xFF625B71),
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 35.dp, top = 25.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Select Icon
            if (!isSelected) {
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .border(
                            width = 1.dp,
                            color = Color.White.copy(alpha = .5f),
                            shape = CircleShape
                        )
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(25.dp)
                        .background(color = Color(0x450F0F0F), shape = CircleShape)
                        .padding(3.dp),
                    imageVector = Icons.Default.Check,
                    contentDescription = "Checked Icon",
                    tint = Color.White
                )
            }

            // Price and Duration
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = data.pricePerMonth,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = "for ${data.duration}",
                fontSize = 14.sp,
                color = Color.White
            )

            // See calculations link
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "See calculations",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}


@Composable
@Preview
fun EMISelectionSlidePreview() {
    EMISelectionSlide()
}


data class CardData(
    val pricePerMonth: String,
    val duration: String,
    val isRecommended: Boolean,
    val backgroundColor: Color
)

val cardItems = listOf(
    CardData(
        "₹5,580 /mo",
        "9 months",
        isRecommended = false,
        backgroundColor = Color(0xFF625B71)
    ),
    CardData(
        "₹4,990 /mo",
        "12 months",
        isRecommended = true,
        backgroundColor = Color(0xFF4A4A6A)
    ),
    CardData(
        "₹6,200 /mo",
        "6 months",
        isRecommended = false,
        backgroundColor = Color(0xFF3C3C54)
    ),
    CardData(
        "₹7,500 /mo",
        "3 months",
        isRecommended = false,
        backgroundColor = Color(0xFF5C5A76)
    )
)
