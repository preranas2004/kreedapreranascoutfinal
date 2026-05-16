package com.example.kreedaprerana.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kreedaprerana.ai.GeminiService
import com.example.kreedaprerana.data.SportsDao
import com.example.kreedaprerana.ui.components.TopCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    athleteId: Int,
    dao: SportsDao,
    gemini: GeminiService,
    onLogTrial: () -> Unit
) {

    // LOAD ATHLETES

    val athletes by dao
        .getAllAthletes()
        .collectAsState(initial = emptyList())

    val athlete =
        athletes.find {
            it.id == athleteId
        }

    // LOAD TRIALS

    val trials by dao
        .getTrialsForAthlete(athleteId)
        .collectAsState(initial = emptyList())

    // AI STATE

    var analysis by remember {
        mutableStateOf("")
    }

    var loading by remember {
        mutableStateOf(false)
    }

    val scope =
        rememberCoroutineScope()

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text =
                            athlete?.name
                                ?: "Profile"
                    )
                }
            )
        },

        floatingActionButton = {

            ExtendedFloatingActionButton(

                onClick = {
                    onLogTrial()
                }
            ) {

                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text("Log Trial")
            }
        }

    ) { padding ->

        LazyColumn(

            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
                .padding(16.dp)

        ) {

            item {

                // TITLE

                Text(

                    text =
                        athlete?.name
                            ?: "Performance Profile",

                    fontSize = 28.sp,

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF07143F)
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // TOP CARD

                TopCard(

                    title =
                        athlete?.primarySport
                            ?: "Sport",

                    value =
                        "${trials.size} Trials"
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // PERFORMANCE CURVE

                Card(

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),

                    shape = RoundedCornerShape(24.dp),

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {

                        Text(

                            text = "Talent Curve",

                            fontWeight = FontWeight.Bold,

                            color = Color.Gray
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {

                            Canvas(
                                modifier = Modifier.fillMaxSize()
                            ) {

                                if (trials.size > 1) {

                                    val values =
                                        trials.map {
                                            it.value.toFloat()
                                        }

                                    val maxVal =
                                        values.maxOrNull() ?: 1f

                                    val minVal =
                                        values.minOrNull() ?: 0f

                                    val range =
                                        (maxVal - minVal)
                                            .coerceAtLeast(0.1f)

                                    val path = Path()

                                    trials.forEachIndexed { index, trial ->

                                        val x =
                                            index * (
                                                    size.width /
                                                            (trials.size - 1)
                                                    )

                                        val y =
                                            size.height - (
                                                    (
                                                            trial.value.toFloat() - minVal
                                                            ) / range
                                                            * size.height
                                                    )

                                        if (index == 0) {

                                            path.moveTo(x, y)

                                        } else {

                                            path.lineTo(x, y)
                                        }

                                        drawCircle(

                                            color = Color(0xFF2962FF),

                                            radius = 10f,

                                            center = Offset(x, y)
                                        )
                                    }

                                    drawPath(

                                        path = path,

                                        color = Color(0xFF2962FF),

                                        style = Stroke(
                                            width = 6f
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                // AI REPORT TITLE

                Text(

                    text = "AI Scout Report",

                    fontWeight = FontWeight.Bold,

                    fontSize = 20.sp
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // AI BUTTON

                Button(

                    onClick = {

                        loading = true

                        scope.launch {

                            analysis =

                                gemini.analyzePerformance(

                                    athleteName =
                                        athlete?.name
                                            ?: "Athlete",

                                    trials = trials
                                )

                            loading = false
                        }
                    }
                ) {

                    Text("Generate AI Report")
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // AI RESULT

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(20.dp)
                ) {

                    Text(

                        text =

                            if (loading)
                                "Generating AI Analysis..."
                            else if (analysis.isEmpty())
                                "Press button to generate AI report"
                            else
                                analysis,

                        modifier = Modifier
                            .padding(16.dp),

                        fontSize = 15.sp
                    )
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                // HISTORY TITLE

                Text(

                    text = "Historical Data",

                    fontWeight = FontWeight.Bold,

                    fontSize = 20.sp
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            // TRIAL LIST

            items(trials) { trial ->

                Card(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),

                    shape = RoundedCornerShape(20.dp),

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {

                    ListItem(

                        headlineContent = {

                            Text(
                                "${trial.trialType}: ${trial.value}"
                            )
                        },

                        supportingContent = {

                            Text(
                                java.util.Date(
                                    trial.timestamp
                                ).toString()
                            )
                        }
                    )
                }
            }
        }
    }
}