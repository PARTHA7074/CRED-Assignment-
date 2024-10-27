package com.partha.credassignment.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
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
import com.partha.credassignment.ui.composableComponents.StackView
import com.partha.credassignment.ui.theme.BackgroundColor
import com.partha.credassignment.ui.theme.CREDAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CREDAssignmentTheme {
                var totalExpendedCount by remember { mutableIntStateOf(0) }

                onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (totalExpendedCount > 0) {
                            totalExpendedCount--
                        } else {
                            isEnabled = false
                            onBackPressedDispatcher.onBackPressed()
                        }
                    }
                })

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding),
                        totalExpendedCount = totalExpendedCount,
                        onToggleState = { position, isExpand ->
                            totalExpendedCount = if (isExpand) position else  position - 1
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    totalExpendedCount: Int,
    onToggleState: (Int, Boolean) -> Unit
) {
    //If contents in the StackView increases then item should be added in this list in same order
    val showSliders = listOf(totalExpendedCount >= 1, totalExpendedCount >= 2)

    Box(modifier = modifier.background(BackgroundColor)) {
        StackView(
            modifier = Modifier.fillMaxSize(),
            showSliders = showSliders,
            contents = listOf(
                { SliderContent(color = Color(0xFF14191d), onClick = { onToggleState(1, false) }) },
                { SliderContent(color = Color(0xFF1a1927), onClick = { onToggleState(2, false) }) },
                { SliderContent(color = Color(0xFF23283c)) }
            )
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                if (totalExpendedCount < showSliders.size) onToggleState(totalExpendedCount + 1, true)
            }
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Continue"
            )
        }
    }
}

@Composable
fun SliderContent(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF14191d),
    onClick: () -> Unit = {}
) {
    var creditAmount by remember { mutableIntStateOf(150000) }
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
            .padding(20.dp)
    ) {
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "credit amount", color = Color.Black, fontSize = 16.sp)
                Text(
                    text = "₹$creditAmount",
                    color = Color.Black,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(text = "@$monthlyRate% monthly", color = Color.Black, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "stash is instant. money will be credited within",
                    color = Color.Black,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StackViewPreview() {
    CREDAssignmentTheme {
        StackView(
            showSliders = listOf(true, true),
            contents = listOf(
                { SliderContent(color = Color(0xFF14191d)) },
                { SliderContent(color = Color(0xFF1a1927)) },
                { SliderContent(color = Color(0xFF23283c)) }
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CREDAssignmentTheme {
        HomeScreen(onToggleState = { index, value -> }, totalExpendedCount = 1)
    }
}
