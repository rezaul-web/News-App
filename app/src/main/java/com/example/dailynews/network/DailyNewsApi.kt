package com.example.dailynews.network

import com.example.dailynews.model.NewsResponse
import com.example.dailynews.utils.Api
import retrofit2.http.GET
import retrofit2.http.Query

interface DailyNewsApi {
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query ("country") country:String="us",
        @Query("category") category: String="business",
        @Query ("apiKey") apiKey:String=Api.API_KEY
    ):NewsResponse
    @GET("v2/everything")
    suspend fun getEverythingBasedOnFromDateAndSorted(
        @Query("q") organization:String="apple",
        @Query ("apiKey") apiKey:String=Api.API_KEY,
        @Query("from") fromDate:String="2024-12-17",
        @Query("sortBy") sortBy:String="popularity",
        @Query("to") toDate:String="2024-12-17"
    ):NewsResponse


}