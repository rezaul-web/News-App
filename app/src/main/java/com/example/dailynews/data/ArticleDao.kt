package com.example.dailynews.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.dailynews.model.ArticleEntity


@Dao
interface ArticleDao {

    @Insert
    suspend fun insert(article: ArticleEntity)

    @Query("SELECT * FROM article_table")
    suspend fun getAllArticles(): List<ArticleEntity>

    @Query("DELETE FROM article_table")
    suspend fun deleteAllArticles()

    @Delete
    suspend fun deleteArticle(article: ArticleEntity)
}
