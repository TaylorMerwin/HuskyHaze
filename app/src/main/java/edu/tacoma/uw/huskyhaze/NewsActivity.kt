package edu.tacoma.uw.huskyhaze

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import edu.tacoma.uw.huskyhaze.network.NewsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        fetchNewsHeadlines()
    }

    private fun fetchNewsHeadlines() {
        val newsService = NewsService.create()
        val apiKey = getString(R.string.news_api_key)
        val country = "us"
        val category = "general"

        CoroutineScope(Dispatchers.IO).launch {
            val response = newsService.getHeadlinesByCategory(country, category, apiKey)
            if (response.isSuccessful) {
                val newsData = response.body()
                if (newsData != null) {
                    // Do something with the news data
                    Log.d("NewsResponse", newsData.toString())
                }
            } else {
                // Handle the error
                Log.e("NewsResponse", response.errorBody().toString())
            }
        }
    }
}