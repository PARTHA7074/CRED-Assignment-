package com.partha.credassignment.ui.composableComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SlideHeader(
    modifier: Modifier = Modifier,
    heading: String,
    subHeading: String
) {
    Column(modifier = modifier){
        Text(
            text = heading,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = subHeading,
            color = Color(0xFFBDBDBD),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun StackedBehindHeaderContent(
    modifier: Modifier = Modifier,
    heading: String,
    subHeading: String
) {
    val textColor = Color.DarkGray
    Column(modifier = modifier) {
        Text(
            text = heading,
            color = textColor,
            fontSize = 12.sp
        )
        Text(
            text = subHeading,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun DropDownArrow(modifier: Modifier = Modifier){
    Icon(
        modifier = modifier,
        imageVector = Icons.Default.ArrowDropDown,
        contentDescription = "Dropdown Arrow",
        tint = Color(0xFFB0B0B0)
    )
}

@Composable
@Preview
fun SlideHeaderPreview() {
    SlideHeader(
        heading = "nikunj, how much do you need?",
        subHeading = "move the dial and set any amount you need up to ₹500000"
    )
}