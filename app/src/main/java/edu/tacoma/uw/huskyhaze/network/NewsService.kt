/**
 * Team 3 - TCSS 450 - Spring 2024
 */
package edu.tacoma.uw.huskyhaze.network

import edu.tacoma.uw.huskyhaze.models.NewsData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    suspend fun getHeadlinesByCategory(
        @Query("country") country: String = "us",
        @Query("category") category: String = "general",
        @Query("apiKey") apiKey: String
    ): Response<NewsData.NewsData>

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsData.NewsData>

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"

        fun create(): NewsService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsService::class.java)
        }
    }
}