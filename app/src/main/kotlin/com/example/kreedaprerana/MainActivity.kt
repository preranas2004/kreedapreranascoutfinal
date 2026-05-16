package com.example.kreedaprerana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.kreedaprerana.ai.GeminiService
import com.example.kreedaprerana.data.SportsDatabase
import com.example.kreedaprerana.ui.screens.AuthScreen
import com.example.kreedaprerana.ui.screens.DashboardScreen
import com.example.kreedaprerana.ui.screens.ProfileScreen
import com.example.kreedaprerana.ui.screens.RankingScreen
import com.example.kreedaprerana.ui.screens.SignupScreen
import com.example.kreedaprerana.ui.screens.TrialLoggerScreen

class MainActivity : ComponentActivity() {

    private lateinit var db: SportsDatabase

    private val geminiService by lazy {
        GeminiService(BuildConfig.GEMINI_API_KEY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            SportsDatabase::class.java,
            "sports-db"
        )
            .fallbackToDestructiveMigration()
            .build()

        setContent {

            MaterialTheme {

                Surface {

                    AppNavigation(
                        db = db,
                        geminiService = geminiService
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigation(
    db: SportsDatabase,
    geminiService: GeminiService
) {

    val navController =
        rememberNavController()

    val dao = db.sportsDao()

    NavHost(
        navController = navController,
        startDestination = "auth"
    ) {

        // LOGIN SCREEN

        composable("auth") {

            AuthScreen(

                onLoginSuccess = {

                    navController.navigate(
                        "dashboard"
                    ) {

                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                },

                onSignupClick = {

                    navController.navigate(
                        "signup"
                    )
                }
            )
        }

        // SIGNUP SCREEN

        composable("signup") {

            SignupScreen(

                onSignupSuccess = {

                    navController.navigate(
                        "dashboard"
                    ) {

                        popUpTo("signup") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // DASHBOARD SCREEN

        composable("dashboard") {

            DashboardScreen(
                dao = dao
            ) { athleteId ->

                if (athleteId == -1) {

                    navController.navigate(
                        "ranking"
                    )

                } else {

                    navController.navigate(
                        "profile/$athleteId"
                    )
                }
            }
        }

        // RANKING SCREEN

        composable("ranking") {

            RankingScreen(
                dao = dao
            )
        }

        // PROFILE SCREEN

        composable(
            route =
                "profile/{athleteId}",

            arguments = listOf(

                navArgument(
                    "athleteId"
                ) {

                    type =
                        NavType.IntType
                }
            )
        ) { backStackEntry ->

            val athleteId =
                backStackEntry.arguments
                    ?.getInt(
                        "athleteId"
                    ) ?: 0

            ProfileScreen(

                athleteId = athleteId,

                dao = dao,

                gemini = geminiService,

                onLogTrial = {

                    navController.navigate(
                        "logger/$athleteId"
                    )
                }
            )
        }

        // LOGGER SCREEN

        composable(
            route =
                "logger/{athleteId}",

            arguments = listOf(

                navArgument(
                    "athleteId"
                ) {

                    type =
                        NavType.IntType
                }
            )
        ) { backStackEntry ->

            val athleteId =
                backStackEntry.arguments
                    ?.getInt(
                        "athleteId"
                    ) ?: 0

            TrialLoggerScreen(

                athleteId = athleteId,

                dao = dao,

                onComplete = {

                    navController.popBackStack()
                }
            )
        }
    }
}