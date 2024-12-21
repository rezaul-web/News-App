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
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

  private  val _articleClicked=MutableStateFlow<Article?>(null)
    val articleClicked=_articleClicked
    fun updateArticleClicked(article: Article) {
        _articleClicked.value=article
    }

    // Method to update the search query
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        // Add logic to fetch filtered results based on query if needed
    }

    val sortBy = MutableStateFlow<String>("publishedAt")

    init {
        // Fetch the headlines during ViewModel initialization
        fetchNews(_searchQuery.value)
        getEverythingFromDateAndSorted(sortBy = sortBy.value)
    }

    fun fetchNews(search:String) {
        viewModelScope.launch {
            newsRepo.getNewsResponse(search).collect {
                _result.value = it
            }
        }
    }

    private fun getEverythingFromDateAndSorted(sortBy: String) {
        viewModelScope.launch {
            newsRepo.getEverythingBasedOnFromDateAndSorted(sortedBy = sortBy).collect {
                _everythingFromAndSorted.value = it
            }
        }
    }

    fun updateSortedBy(s: String) {
        sortBy.value = s
        getEverythingFromDateAndSorted(sortBy = s)

    }
}

