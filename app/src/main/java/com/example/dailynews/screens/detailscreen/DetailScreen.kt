package com.example.dailynews.screens.detailscreen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.dailynews.model.Article
import com.example.dailynews.model.toEntity
import com.example.dailynews.utils.formatDate
import com.example.dailynews.viewmodels.ArticleViewModel
import com.example.dailynews.viewmodels.HomeViewmodel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewmodel: HomeViewmodel,
    articleViewmodel: ArticleViewModel= hiltViewModel()
) {
    val article by homeViewmodel.articleClicked.collectAsState()
    val context = LocalContext.current
    val saved= remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            DetailScreenTopBar(
                onBackClick = { navController.navigateUp() },
                onSaveClick = {
                    article?.let { articleViewmodel.addArticle(it.toEntity()) }
                    Toast.makeText(context, "Article Saved", Toast.LENGTH_SHORT).show()
                    saved.value=true

                },
                buttonTint = {
                    if (saved.value){
                        Color.Gray
                    }else{
                        Color.Red
                    }
                },
                enabled = !saved.value
            )
        }
    ) { paddingValues ->
        // Add padding to the content to prevent overlap with the top bar
        article?.let { article ->
            DetailContent(
                article = article,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(top = 15.dp)// Use padding from Scaffold to avoid overlap
                    .padding(horizontal = 16.dp) // Add additional horizontal padding if needed
            ) { url ->
                openArticleLink(context, url)
            }
        } ?: NoArticleAvailable()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenTopBar(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    buttonTint :()->Color={Color.Red},
    enabled:Boolean=true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp) // Custom height for the top bar
            .background(Color.Cyan), // Set the background color
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Back button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(start = 8.dp) ,
            enabled = true// Add padding for alignment
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Go Back",
                tint = Color.Black
            )
        }

        // Title
        Text(
            text = "Article Details",
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge, // Adjust the typography style
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
            maxLines = 1
        )

        // Save button
        IconButton(
            onClick = onSaveClick,
            modifier = Modifier.padding(end = 8.dp),
            enabled = enabled
        // Add padding for alignment
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Save Article",
                tint =buttonTint()
            )
        }
    }
}


@Composable
fun DetailContent(
    article: Article,
    modifier: Modifier = Modifier,
    onReadMoreClick: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        article.urlToImage?.let {
            AsyncImage(
                model = it,
                contentDescription = "Article Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = article.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        article.author?.let {
            Text(
                text = "Author: $it",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        article.source?.name?.let {
            Text(
                text = "Source: $it",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Text(
            text = "Published At: ${formatDate(article.publishedAt)}",
            style = MaterialTheme.typography.bodySmall
        )

        article.description?.let {
            Text(
                text = "Description: $it",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        article.content?.let {
            val formattedContent = it.trimIndent().replace("/", " ").replace("\n", " ")
            Text(
                text = "Content: $formattedContent",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }

        TextButton(onClick = { onReadMoreClick(article.url) }) {
            Text(
                text = "Read Full Article",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun NoArticleAvailable() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "No article data available",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

fun openArticleLink(context: android.content.Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Invalid URL: $e", Toast.LENGTH_SHORT).show()
    }
}
