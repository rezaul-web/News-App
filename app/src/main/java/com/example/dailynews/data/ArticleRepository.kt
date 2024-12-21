package com.example.dailynews.data

import com.example.dailynews.model.ArticleEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject constructor(private val articleDao: ArticleDao) {

    suspend fun insert(article: ArticleEntity) {
        articleDao.insert(article)
    }

    suspend fun getAllArticles(): List<ArticleEntity> {
        return articleDao.getAllArticles()
    }

    suspend fun deleteAllArticles() {
        articleDao.deleteAllArticles()
    }
    suspend fun deleteArticle(article: ArticleEntity) {
        articleDao.deleteArticle(article)
    }
}
