package com.example.dailynews.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dailynews.screens.TitleCard
import com.example.dailynews.viewmodels.HomeViewmodel

@Composable
fun HomeScreen(
    viewModel: HomeViewmodel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val everythingFromAndSorted by viewModel.everythingFromAndSorted.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentSortMethod by viewModel.sortBy.collectAsState()
    val articles = everythingFromAndSorted?.articles ?: emptyList()
    val searchQuery = remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .background(Color(0xFFF5F5F5))
            .fillMaxSize()
    ) {
        if (isLoading) {
            // Center the loading indicator
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = Color.Red,
                    strokeWidth = 4.dp
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                // Search Bar
                OutlinedTextField(
                    onValueChange = { searchQuery.value=it

                    },
                    value = searchQuery.value,
                    label = { Text(text = "Search For Headlines") },
                    trailingIcon = {
                        if (searchQuery.value.isNotEmpty()) {
                            IconButton(onClick = {
                                viewModel.updateSearchQuery(searchQuery.value)
                            }) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )

                // Article List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    items(articles) { article ->
                        if (article.title != "[Removed]") {
                            TitleCard(
                                modifier = Modifier.padding(4.dp),
                                article = article,
                                onClick = {
                                    viewModel.updateArticleClicked(article)
                                    navController.navigate("detail_screen")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
