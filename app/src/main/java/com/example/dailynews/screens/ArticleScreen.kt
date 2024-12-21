package com.example.dailynews.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dailynews.model.Article
import com.example.dailynews.viewmodels.ArticleViewModel

@Composable
fun ArticleScreen(viewModel: ArticleViewModel = hiltViewModel(),navController: NavController) {
    val articles by viewModel.articles.observeAsState(emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {


        }) {
            Text(text = "Add Article")
        }

        LazyColumn {
           items(articles) {
               TitleCard(modifier = Modifier, navController = navController, article = it, onClick = {})
           }
        }
    }
}
