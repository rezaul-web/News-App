package com.example.dailynews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailynews.data.ArticleRepository
import com.example.dailynews.model.Article
import com.example.dailynews.model.ArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val repository: ArticleRepository) : ViewModel() {

    private val _articles = MutableStateFlow<List<ArticleEntity>>(emptyList())
    val articles = _articles

    private  val _articleClicked=MutableStateFlow<Article?>(null)
    val articleClicked=_articleClicked
    fun updateArticleClicked(article: Article) {
        _articleClicked.value=article
    }
init {
    getAllArticles()

}
    private fun getAllArticles() {
        viewModelScope.launch {
            _articles.value = repository.getAllArticles()
        }
    }

    fun addArticle(article: ArticleEntity) {
        viewModelScope.launch {
            repository.insert(article)
            getAllArticles() // Refresh the list
        }
    }

    fun deleteAllArticles() {
        viewModelScope.launch {
            repository.deleteAllArticles()
            getAllArticles() // Refresh the list
        }
    }
    fun deleteArticle(article: ArticleEntity) {
        viewModelScope.launch {
            repository.deleteArticle(article)
            getAllArticles() // Refresh the list
        }
    }
}
