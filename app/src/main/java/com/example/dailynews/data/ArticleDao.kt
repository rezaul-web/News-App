package com.example.dailynews.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.dailynews.model.Article
import com.example.dailynews.model.NewsResponse
import com.example.dailynews.repository.NewsRepo
import javax.inject.Inject

@Dao
interface ArticleDao {

    @Insert
    suspend fun insert(article: Article)

    @Query("SELECT * FROM article_table")
    suspend fun getAllArticles(): List<Article>

    @Query("DELETE FROM article_table")
    suspend fun deleteAllArticles()
}
