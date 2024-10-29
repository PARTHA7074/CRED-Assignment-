package com.partha.credassignment.ui.composableComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.partha.credassignment.R
import com.partha.credassignment.ui.activities.showNotImplementedToast

@Composable
fun BankAccountSelectionSlide(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF23283c),
    onClick: () -> Unit = {},
    isStackedBehind: Boolean = false
) {
    val context = LocalContext.current

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
        if (isStackedBehind) {
            // Stacked behind header content
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                SlideHeader(
                    modifier = Modifier.padding(vertical = 10.dp),
                    heading = "where should we send the money?",
                    subHeading = "amount will be credited to this bank account, EMI will also be debited from this bank account"
                )

                BankAccountRow(modifier = Modifier.padding(top = 16.dp))

                OutlinedButton(
                    modifier = Modifier.padding(top = 30.dp),
                    onClick = { showNotImplementedToast(context) }
                ) {
                    Text(
                        text = "Change account",
                        color = Color.White.copy(alpha = .7f)
                    )
                }
            }
        }
    }
}

@Composable
fun BankAccountRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Bank Icon
            Image(
                painter = painterResource(id = R.drawable.ic_hdfc_logo),
                contentDescription = "Bank Logo",
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Bank Name and Account Number
            Column {
                Text(
                    text = "HDFC Bank",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "50100117009192",
                    color = Color(0xFFB0B0C3),
                    fontSize = 14.sp
                )
            }
        }

        // Checkmark Icon
        Icon(
            modifier = Modifier
                .size(25.dp)
                .background(color = Color(0xFF38404b), shape = CircleShape)
                .padding(3.dp),
            imageVector = Icons.Default.Check,
            contentDescription = "Selected",
            tint = Color.White
        )
    }
}


@Composable
@Preview
fun BankAccountSelectionSlidePreview() {
    BankAccountSelectionSlide()
}