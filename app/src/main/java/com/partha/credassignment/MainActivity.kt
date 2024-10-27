package com.partha.credassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.partha.credassignment.ui.theme.CREDAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CREDAssignmentTheme {
                var showSecondSlider by remember { mutableStateOf(false) }
                var showThirdSlider by remember { mutableStateOf(false) }

                onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        when {
                            showThirdSlider -> showThirdSlider = false
                            showSecondSlider -> showSecondSlider = false
                            else -> {
                                isEnabled = false
                                onBackPressedDispatcher.onBackPressed()
                            }
                        }
                    }
                })

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding),
                        showSecondSlider = showSecondSlider,
                        showThirdSlider = showThirdSlider,
                        onToggleSlider = { showSecondSlider = it },
                        onToggleThirdSlider = { showThirdSlider = it }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    showSecondSlider: Boolean,
    showThirdSlider: Boolean,
    onToggleSlider: (Boolean) -> Unit,
    onToggleThirdSlider: (Boolean) -> Unit
) {
    Box(modifier = modifier) {
        StackView(
            modifier = Modifier.fillMaxSize(),
            showSecondSlider = showSecondSlider,
            showThirdSlider = showThirdSlider,
            onSliderClick = { sliderNumber ->
                when (sliderNumber) {
                    1 -> {
                        if (showSecondSlider) onToggleSlider(false)
                        if (showThirdSlider) onToggleThirdSlider(false)
                    }
                    2 -> if (showThirdSlider) onToggleThirdSlider(false)
                }
            }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                if (showSecondSlider) onToggleThirdSlider(true)
                else onToggleSlider(true)
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
fun StackView(
    modifier: Modifier = Modifier,
    showSecondSlider: Boolean,
    showThirdSlider: Boolean,
    onSliderClick: (Int) -> Unit
) {
    Box(modifier = modifier.padding(top = 50.dp)) {
        CreditAmountSlider(onClick = { onSliderClick(1) })

        AnimatedVisibility(
            visible = showSecondSlider,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            SliderContent(
                modifier = Modifier.padding(top = 70.dp),
                color = Color(0xFF504040),
                onClick = { onSliderClick(2) }
            )
        }

        AnimatedVisibility(
            visible = showThirdSlider,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            SliderContent(
                modifier = Modifier.padding(top = 140.dp),
                color = Color(0xFF3F2B2B)
            )
        }
    }
}

@Composable
fun CreditAmountSlider(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF101010), shape = RoundedCornerShape(16.dp))
        .padding(16.dp),
    onClick: () -> Unit = {}
) {
    SliderContent(modifier = modifier, onClick = onClick)
}

@Composable
fun SliderContent(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF101010),
    onClick: () -> Unit = {}
) {
    var creditAmount by remember { mutableStateOf(150000) }
    val maxCreditAmount = 500000
    val monthlyRate = 1.04f

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
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
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
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

@Preview(showBackground = true)
@Composable
fun StackViewPreview() {
    CREDAssignmentTheme {
        StackView(showSecondSlider = true, showThirdSlider = true, onSliderClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CreditAmountSliderPreview() {
    CREDAssignmentTheme {
        CreditAmountSlider()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CREDAssignmentTheme {
        HomeScreen(onToggleSlider = {}, showSecondSlider = false, showThirdSlider = false, onToggleThirdSlider = {})
    }
}
