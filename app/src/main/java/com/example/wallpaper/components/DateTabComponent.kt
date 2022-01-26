package com.example.wallpaper.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpaper.ui.theme.Black30

@Composable
fun DateTabComponent(
    modifier: Modifier = Modifier,
    day: String = "Wednesday",
    date: String = "22 December",
    time: String = "22:12",
) {

    val tabShape = RoundedCornerShape(8.dp)

    Box(
        modifier = modifier
            .clip(tabShape)
            .background(Black30)
            .padding(10.dp, 20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                val textColor = Color.White
                val textSize = 12.sp
                val fontWeight = FontWeight.SemiBold

                Text(
                    text = day,
                    color = textColor,
                    fontSize = textSize,
                    fontWeight = fontWeight
                )

                Text(
                    text = date,
                    color = textColor,
                    fontSize = textSize,
                    fontWeight = fontWeight
                )
            }

            Text(
                text = time,
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

    }

}

@Preview
@Composable
fun DateTabComponentPreview() {
    DateTabComponent()
}