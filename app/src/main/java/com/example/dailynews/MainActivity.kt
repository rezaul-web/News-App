package com.example.dailynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dailynews.screens.detailscreen.DetailScreen
import com.example.dailynews.screens.HomeScreen
import com.example.dailynews.screens.HomeScreenTopBar
import com.example.dailynews.ui.theme.DailyNewsTheme
import com.example.dailynews.viewmodels.HomeViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel: HomeViewmodel by viewModels()

            // Track the current route for controlling the top bar visibility
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

            // State to control the top bar visibility
            val showTopBar = remember { mutableStateOf(true) }

            // Update top bar visibility based on the current route
            LaunchedEffect(currentRoute) {
                showTopBar.value = when (currentRoute) {
                    "home" -> true
                    "detail_screen" -> false
                    else -> false
                }
            }

            DailyNewsTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        // Conditionally show the top bar for the home screen route
                        if (showTopBar.value) {
                            val currentSortMethod = viewModel.sortBy.collectAsState().value
                            HomeScreenTopBar(viewModel = viewModel, currentSortMethod = currentSortMethod)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Home Screen Route
                        composable(route = "home") {
                            HomeScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        // Detail Screen Route
                        composable(route = "detail_screen") {
                            DetailScreen(
                                navController = navController,
                                homeViewmodel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
