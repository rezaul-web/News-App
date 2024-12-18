package com.example.dailynews.repository

import com.example.dailynews.model.NewsResponse
import com.example.dailynews.network.DailyNewsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepo @Inject constructor(
    private val dailyNewsApi: DailyNewsApi
) {
    fun getNewsResponse(category:String): Flow<NewsResponse> {
        return flow {
            emit(dailyNewsApi.getNews(category=category))
        }.catch {
            // Handle exceptions
        }
    }
    fun getEverythingBasedOnFromDateAndSorted(sortedBy:String="popularity"): Flow<NewsResponse> {
        return flow {
            emit(dailyNewsApi.getEverythingBasedOnFromDateAndSorted(sortBy = sortedBy))
        }.catch {
            // Handle exceptions
        }
    }
}

