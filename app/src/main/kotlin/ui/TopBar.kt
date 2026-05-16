package com.example.kreedaprerana.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopCard(
    title: String,
    value: String
) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2962FF)
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Row(
                horizontalArrangement =
                    Arrangement.SpaceBetween,

                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = title,

                    color = Color.White,

                    fontSize = 18.sp
                )

                Icon(
                    imageVector =
                        Icons.Default.EmojiEvents,

                    contentDescription = null,

                    tint = Color.Yellow
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = value,

                color = Color.White,

                fontSize = 32.sp,

                fontWeight = FontWeight.Bold
            )
        }
    }
}