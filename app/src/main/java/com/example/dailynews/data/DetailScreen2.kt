package com.example.dailynews.data

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.dailynews.model.Article
import com.example.dailynews.screens.detailscreen.DetailContent
import com.example.dailynews.screens.detailscreen.NoArticleAvailable
import com.example.dailynews.screens.detailscreen.openArticleLink
import com.example.dailynews.utils.formatDate
import com.example.dailynews.viewmodels.ArticleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen2(
    modifier: Modifier = Modifier,
    navController: NavController,
    articleViewmodel: ArticleViewModel
) {
    val article by articleViewmodel.articleClicked.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail Screen") },
                actions = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor =Color.Cyan,
                    titleContentColor = Color.Black
                ),
                )
        }

    ) { paddingValues ->
        // Add padding to the content to prevent overlap with the top bar
        article?.let { article ->
            DetailContent(
                article = article,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Use padding from Scaffold to avoid overlap
                    .padding(horizontal = 16.dp) // Add additional horizontal padding if needed
            ) { url ->
                openArticleLink(context, url)
            }
        } ?: NoArticleAvailable()
    }
}




