package com.example.kreedaprerana.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kreedaprerana.data.Athlete

@Composable
fun TalentCard(
    athlete: Athlete,
    onClick: () -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,

                modifier = Modifier.weight(1f)
            ) {

                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2962FF)),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Person,

                        contentDescription = null,

                        tint = Color.White,

                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.size(18.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(

                        text = athlete.name,

                        color = Color(0xFF07143F),

                        fontSize = 22.sp,

                        fontWeight = FontWeight.Bold,

                        maxLines = 1,

                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Text(
                            text = athlete.primarySport,

                            color = Color(0xFF2962FF),

                            fontWeight = FontWeight.SemiBold,

                            modifier = Modifier
                                .background(
                                    Color(0xFFE8F0FF),
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 6.dp
                                )
                        )

                        Spacer(
                            modifier = Modifier.size(10.dp)
                        )

                        Text(
                            text = "${athlete.age} yrs",

                            color = Color.Gray,

                            fontSize = 15.sp
                        )
                    }
                }
            }

            Icon(
                imageVector =
                    Icons.Default.KeyboardArrowRight,

                contentDescription = null,

                tint = Color.Gray,

                modifier = Modifier.size(30.dp)
            )
        }
    }
}