package com.example.kreedaprerana.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kreedaprerana.data.Athlete
import com.example.kreedaprerana.data.SportsDao
import com.example.kreedaprerana.ui.components.TalentCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    dao: SportsDao,
    onAthleteClick: (Int) -> Unit
) {

    val athletes by dao
        .getAllAthletes()
        .collectAsState(initial = emptyList())

    val scope = rememberCoroutineScope()

    val snackbarHostState =
        remember {
            SnackbarHostState()
        }

    var showDialog by remember {
        mutableStateOf(false)
    }

    var athleteName by remember {
        mutableStateOf("")
    }

    var athleteAge by remember {
        mutableStateOf("")
    }

    var athleteSport by remember {
        mutableStateOf("")
    }

    // ADD ATHLETE DIALOG

    if (showDialog) {

        AlertDialog(

            onDismissRequest = {
                showDialog = false
            },

            title = {
                Text("Add Athlete")
            },

            text = {

                Column {

                    OutlinedTextField(

                        value = athleteName,

                        onValueChange = {
                            athleteName = it
                        },

                        label = {
                            Text("Athlete Name")
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    OutlinedTextField(

                        value = athleteAge,

                        onValueChange = {
                            athleteAge = it
                        },

                        label = {
                            Text("Age")
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    OutlinedTextField(

                        value = athleteSport,

                        onValueChange = {
                            athleteSport = it
                        },

                        label = {
                            Text("Sport")
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            },

            confirmButton = {

                Button(

                    onClick = {

                        val cleanName =
                            athleteName.trim()

                        val cleanSport =
                            athleteSport.trim()

                        val ageInt =
                            athleteAge
                                .trim()
                                .toIntOrNull()

                        if (
                            cleanName.isEmpty() ||
                            cleanSport.isEmpty() ||
                            ageInt == null
                        ) {

                            scope.launch {

                                snackbarHostState
                                    .showSnackbar(
                                        "Please enter valid details"
                                    )
                            }

                            return@Button
                        }

                        scope.launch {

                            dao.insertAthlete(

                                Athlete(

                                    name = cleanName,

                                    age = ageInt,

                                    primarySport = cleanSport
                                )
                            )

                            snackbarHostState
                                .showSnackbar(
                                    "Athlete Added Successfully"
                                )
                        }

                        athleteName = ""
                        athleteAge = ""
                        athleteSport = ""

                        showDialog = false
                    }
                ) {

                    Text("SAVE")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        showDialog = false
                    }
                ) {

                    Text("CANCEL")
                }
            }
        )
    }

    Scaffold(

        snackbarHost = {

            SnackbarHost(
                hostState = snackbarHostState
            )
        },

        topBar = {

            TopAppBar(

                title = {

                    Text(

                        text = "Kreeda Dashboard",

                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },

        floatingActionButton = {

            ExtendedFloatingActionButton(

                onClick = {

                    showDialog = true
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Add,

                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(6.dp)
                )

                Text("Add Athlete")
            }
        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(

                text = "Active Athletes",

                fontSize = 28.sp,

                fontWeight = FontWeight.Bold,

                color = Color(0xFF07143F)
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // OPEN RANKINGS BUTTON

            Button(

                onClick = {

                    onAthleteClick(-1)
                }
            ) {

                Text("Open Rankings")
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            if (athletes.isEmpty()) {

                Text(

                    text = "No athletes added yet.",

                    color = Color.Gray
                )
            }

            LazyColumn(

                modifier = Modifier.fillMaxSize(),

                contentPadding =
                    PaddingValues(bottom = 100.dp),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                items(athletes) { athlete ->

                    TalentCard(

                        athlete = athlete,

                        onClick = {

                            onAthleteClick(
                                athlete.id
                            )
                        }
                    )
                }
            }
        }
    }
}