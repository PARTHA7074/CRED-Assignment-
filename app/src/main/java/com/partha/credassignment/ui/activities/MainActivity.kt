package com.partha.credassignment.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.partha.credassignment.ui.composableComponents.CreditAmountSlide
import com.partha.credassignment.ui.composableComponents.EMISelectionSlide
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
                { CreditAmountSlide(color = Color(0xFF14191d), onClick = { onToggleState(1, false) }, isStackedBehind = totalExpendedCount>0) },
                { EMISelectionSlide(color = Color(0xFF1a1927), onClick = { onToggleState(2, false) }, isStackedBehind = totalExpendedCount>1) },
                { CreditAmountSlide(color = Color(0xFF23283c)) }
            )
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                if (totalExpendedCount < showSliders.size) onToggleState(totalExpendedCount + 1, true)
            }
        ) {
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = when(totalExpendedCount){
                    1 -> "Select your bank account"
                    2 -> "Tap for one click KYC"
                    else -> "Proceed to EMI selection"
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CREDAssignmentTheme {
        HomeScreen(onToggleState = { index, value -> }, totalExpendedCount = 1)
    }
}
