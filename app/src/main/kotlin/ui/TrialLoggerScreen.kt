package com.example.kreedaprerana.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kreedaprerana.data.SportsDao
import com.example.kreedaprerana.data.Trial
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TrialLoggerScreen(
    athleteId: Int,
    dao: SportsDao,
    onComplete: () -> Unit
) {

    var time by remember {
        mutableLongStateOf(0L)
    }

    var isRunning by remember {
        mutableStateOf(false)
    }

    var trialType by remember {
        mutableStateOf("100m Sprint")
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(isRunning) {

        if (isRunning) {

            val startTime = System.currentTimeMillis() - time

            while (isRunning) {

                time = System.currentTimeMillis() - startTime

                delay(10)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Card(
            elevation = CardDefaults.cardElevation(6.dp)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Trial Logger",

                    fontSize = 28.sp,

                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = String.format("%.2f", time / 1000.0),

                    fontSize = 50.sp,

                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = trialType,

                    onValueChange = {
                        trialType = it
                    },

                    label = {
                        Text("Trial Type")
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))

                Row {

                    Button(
                        onClick = {
                            isRunning = !isRunning
                        }
                    ) {

                        Text(
                            if (isRunning)
                                "STOP"
                            else
                                "START"
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = {

                            time = 0

                            isRunning = false
                        }
                    ) {

                        Text("RESET")
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {

                        scope.launch {

                            dao.insertTrial(

                                Trial(
                                    athleteId = athleteId,

                                    trialType = trialType,

                                    value = time / 1000.0
                                )
                            )

                            onComplete()
                        }
                    }
                ) {

                    Text("SAVE RECORD")
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}