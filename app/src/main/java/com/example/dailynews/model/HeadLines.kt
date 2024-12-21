package com.example.dailynews.model

import androidx.room.Entity
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article> // Ensure this is a list of `Article`
)
//@Entity(tableName = "article_table")
data class Article(
    val source: Source?,
    val author: String?, // Nullable
    val title: String,
    val description: String?, // Nullable
    val url: String,
    val urlToImage: String?, // Nullable
    val publishedAt: String,
    val content: String? // Nullable
)




data class Source(
    val id: String?, // Nullable
    val name: String?
)
