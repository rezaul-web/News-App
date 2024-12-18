package com.example.dailynews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailynews.data.ArticleRepository
import com.example.dailynews.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val repository: ArticleRepository) : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private fun getAllArticles() {
        viewModelScope.launch {
            _articles.value = repository.getAllArticles()
        }
    }

    fun addArticle(article: Article) {
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
}
