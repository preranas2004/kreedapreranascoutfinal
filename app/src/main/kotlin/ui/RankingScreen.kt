package com.example.kreedaprerana.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kreedaprerana.data.SportsDao
import com.example.kreedaprerana.ui.components.TalentCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(
    dao: SportsDao
) {

    val athletes by dao
        .getAllAthletes()
        .collectAsState(initial = emptyList())

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Athlete Rankings"
                    )
                }
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
                .padding(padding)
                .padding(16.dp)
        ) {

            LazyColumn(

                contentPadding =
                    PaddingValues(bottom = 100.dp),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                itemsIndexed(athletes) { index, athlete ->

                    Column {

                        Text(
                            text = "#${index + 1}",

                            fontWeight = FontWeight.Bold,

                            fontSize = 18.sp,

                            color = Color(0xFF2962FF)
                        )

                        TalentCard(
                            athlete = athlete,
                            onClick = {}
                        )
                    }
                }
            }
        }
    }
}