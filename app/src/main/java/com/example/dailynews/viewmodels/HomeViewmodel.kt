package com.example.dailynews.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailynews.model.Article
import com.example.dailynews.model.NewsResponse
import com.example.dailynews.repository.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val newsRepo: NewsRepo
) : ViewModel() {

    val isLoading = MutableStateFlow<Boolean>(false)
    private val _result = MutableStateFlow<NewsResponse?>(null)
    val result: StateFlow<NewsResponse?> = _result
    private val _everythingFromAndSorted = MutableStateFlow<NewsResponse?>(null)
    val everythingFromAndSorted: StateFlow<NewsResponse?> = _everythingFromAndSorted
    private val _searchQuery = MutableStateFlow("microsoft")

    private val _articleClicked = MutableStateFlow<Article?>(null)
    val articleClicked = _articleClicked
    fun updateArticleClicked(article: Article) {
        _articleClicked.value = article
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        fetchNews(query)
        getEverythingFromDateAndSorted(sortBy = sortBy.value, organization = query)
    }

    val sortBy = MutableStateFlow<String>("publishedAt")

    init {
        fetchNews(_searchQuery.value)
        getEverythingFromDateAndSorted(sortBy = sortBy.value, organization = _searchQuery.value)
    }

    private fun fetchNews(search: String) {
        viewModelScope.launch {
            newsRepo.getNewsResponse(search).collect {
                _result.value = it
            }
        }
    }

    private fun getEverythingFromDateAndSorted(sortBy: String, organization: String) {
        viewModelScope.launch {
            isLoading.value=true
            newsRepo.getEverythingBasedOnFromDateAndSorted(sortedBy = sortBy, organization = organization).collect {
                isLoading.value=false
                _everythingFromAndSorted.value = it

            }
        }
    }

    fun updateSortedBy(s: String) {
        sortBy.value = s
        getEverythingFromDateAndSorted(sortBy = s, organization = _searchQuery.value)
    }
}
