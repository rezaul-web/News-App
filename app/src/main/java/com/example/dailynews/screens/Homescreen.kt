package com.example.dailynews.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.dailynews.model.Article
import com.example.dailynews.viewmodels.ArticleViewModel
import com.example.dailynews.viewmodels.HomeViewmodel

@OptIn(ExperimentalMaterial3Api::class)
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


    val searchQuery by viewModel.searchQuery.collectAsState()
   // val searchQuery = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily News", color = Color.White) },
                actions = {
                    // Popularity Button
                    TextButton(onClick = { viewModel.updateSortedBy("popularity") }) {
                        Text(
                            text = if (currentSortMethod == "popularity") "Popularity ✓" else "Popularity",
                            color = Color.White
                        )
                    }

                    // PublishedAt Button
                    TextButton(onClick = { viewModel.updateSortedBy("publishedAt") }) {
                        Text(
                            text = if (currentSortMethod == "publishedAt") "PublishedAt ✓" else "PublishedAt",
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .background(Color(0xFFF5F5F5)),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                if (isLoading) {
                    Text("Loading...", fontSize = 16.sp, modifier = Modifier.padding(16.dp))
                } else {
                    LazyColumn {
                        item {
                            OutlinedTextField(
                                onValueChange = {query->

                                    viewModel.updateSearchQuery(query)
                                },
                                value = searchQuery,
                                label = {
                                    Text(text = "Search For Headlines")
                                },
                                trailingIcon = {
                                    if(searchQuery.isNotEmpty())
                                    {
                                        IconButton(onClick = {

                                        }) {
                                            Icon(
                                                Icons.Default.Search,
                                                contentDescription = null,
                                            )
                                        }

                                    }

                                },

                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .padding(start = 8.dp, end = 8.dp)
                            )
                        }
                        items(articles) { article ->
                            if (article.title != "[Removed]") {
                                TitleCard (
                                    modifier = Modifier.padding(4.dp),
                                    article = article,
                                    navController = navController,

                                )
                            }
                        }
                    }
                }
            }
        }
    )

}



@Composable
fun TitleCard(modifier: Modifier = Modifier, article: Article, navController: NavController) {
    Card(
        onClick = {

        }, modifier = Modifier.padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Row(modifier = modifier.fillMaxWidth()) {
                AsyncImage(
                    model = article.urlToImage,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.CenterVertically),
                    contentScale = ContentScale.Crop
                )
                Column {
                    article.author?.let {
                        Text(
                            text = it,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                    Text(text = article.title, modifier = Modifier.padding(16.dp))
                }

            }
        }

    }

}
