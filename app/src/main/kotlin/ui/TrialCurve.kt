package com.example.kreedaprerana.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kreedaprerana.data.Trial

@Composable
fun TrialCurve(
    trials: List<Trial>
) {

    if (trials.isEmpty()) {

        Card(
            modifier = Modifier.fillMaxWidth(),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {

            Text(
                text = "No trial data available",

                modifier = Modifier.padding(20.dp),

                color = Color.Gray
            )
        }

        return
    }

    val values = trials.map {
        it.value.toFloat()
    }

    val maxValue = values.maxOrNull() ?: 1f

    Card(

        modifier = Modifier
            .fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Text(
            text = "Performance Trend",

            modifier = Modifier.padding(
                start = 16.dp,
                top = 16.dp
            ),

            fontWeight = FontWeight.Bold,

            fontSize = 20.sp
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .background(Color.White)
                .padding(20.dp)
        ) {

            val width = size.width
            val height = size.height

            val spacing =
                width / (values.size - 1).coerceAtLeast(1)

            val path = Path()

            values.forEachIndexed { index, value ->

                val x = spacing * index

                val y =
                    height -
                            (value / maxValue) * height

                if (index == 0) {

                    path.moveTo(x, y)

                } else {

                    path.lineTo(x, y)
                }

                drawCircle(
                    color = Color(0xFF2962FF),

                    radius = 8f,

                    center = Offset(x, y)
                )
            }

            drawPath(

                path = path,

                color = Color(0xFF2962FF),

                style = Stroke(
                    width = 6f,
                    cap = StrokeCap.Round
                )
            )
        }
    }
}