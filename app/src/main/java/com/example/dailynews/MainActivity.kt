package com.example.dailynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dailynews.screens.detailscreen.DetailScreen
import com.example.dailynews.screens.HomeScreen
import com.example.dailynews.ui.theme.DailyNewsTheme
import com.example.dailynews.viewmodels.HomeViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyNewsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: HomeViewmodel by viewModels()
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        // Home Screen Route
                        composable(route = "home") {
                            HomeScreen(
                                viewModel = viewModel,
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        // Detail Screen Route
                        composable(route = "detail_screen") {
                            DetailScreen(
                                homeViewmodel = viewModel,
                                navController = navController
                            )
                        }
                    }

                }

            }
        }
    }
}

