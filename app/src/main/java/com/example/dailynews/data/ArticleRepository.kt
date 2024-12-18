package com.example.dailynews.data

import com.example.dailynews.model.Article
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject constructor(private val articleDao: ArticleDao) {

    suspend fun insert(article: Article) {
        articleDao.insert(article)
    }

    suspend fun getAllArticles(): List<Article> {
        return articleDao.getAllArticles()
    }

    suspend fun deleteAllArticles() {
        articleDao.deleteAllArticles()
    }
}
