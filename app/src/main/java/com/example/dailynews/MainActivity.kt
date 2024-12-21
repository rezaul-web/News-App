package com.example.dailynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dailynews.data.DetailScreen2
import com.example.dailynews.data.TitleCard2
import com.example.dailynews.model.toDomain
import com.example.dailynews.screens.TitleCard
import com.example.dailynews.screens.detailscreen.DetailScreen
import com.example.dailynews.screens.home.HomeScreen
import com.example.dailynews.screens.home.HomeScreenTopBar
import com.example.dailynews.ui.theme.DailyNewsTheme
import com.example.dailynews.utils.DeleteAlertDialog
import com.example.dailynews.viewmodels.ArticleViewModel
import com.example.dailynews.viewmodels.HomeViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel: HomeViewmodel by viewModels()
            val articleViewmodel: ArticleViewModel by viewModels()

            // Track the current route for controlling the top bar visibility
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route

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
                val scope = rememberCoroutineScope()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        SideDrawerContent(
                            onCloseDrawer = {
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            articleViewModel = articleViewmodel,
                            navController=navController
                        )
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            // Conditionally show the top bar for the home screen route
                            if (showTopBar.value) {
                                val currentSortMethod = viewModel.sortBy.collectAsState().value
                                HomeScreenTopBar(
                                    viewModel = viewModel,
                                    currentSortMethod = currentSortMethod,
                                    onMenuClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    })
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
                                    homeViewmodel = viewModel,
                                    articleViewmodel = articleViewmodel
                                )
                            }
                            composable(route = "detail_screen2") {
                                DetailScreen2(
                                    navController = navController,
                                    articleViewmodel = articleViewmodel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SideDrawerContent(
    onCloseDrawer: () -> Unit,
    articleViewModel: ArticleViewModel = hiltViewModel(),
    navController: NavController
) {
    val favoriteArticles = articleViewModel.articles.collectAsState()
    val showDeleteDialog = remember { mutableStateOf(false) }
    val showDeleteDialog2 = remember { mutableStateOf(false) }

    ModalDrawerSheet {
        // Drawer content
      Row (modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
          )   {
            Text(
                "Favorite Articles",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
          IconButton(onClick = {
              showDeleteDialog2.value=true
          }) {
              Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red, modifier = Modifier.size(35.dp))
              if (showDeleteDialog2.value) {
                  DeleteAlertDialog(
                      dialogText = "You are about to delete All the articles",
                      onDismissRequest = {
                          showDeleteDialog2.value = false
                      },
                      onConfirmation = {
                          articleViewModel.deleteAllArticles()
                          showDeleteDialog2.value = false

                      },
                  )
              }
          }
        }
        HorizontalDivider()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            items(favoriteArticles.value.reversed()) { article ->
                if (article.title != "[Removed]") {
                    TitleCard2(
                        modifier = Modifier.padding(4.dp),
                        article = article.toDomain(),
                        onClick = {
                            articleViewModel.updateArticleClicked(article.toDomain())
                            navController.navigate("detail_screen2")
                            onCloseDrawer()
                        },
                        onLongClick = {
                            showDeleteDialog.value=true
                        }

                    )
                    if (showDeleteDialog.value) {
                        DeleteAlertDialog(
                            onDismissRequest = {
                                showDeleteDialog.value = false
                            },
                            onConfirmation = {
                                articleViewModel.deleteArticle(article)
                                showDeleteDialog.value = false

                            },
                        )
                    }
                }
            }

        }

    }
}

