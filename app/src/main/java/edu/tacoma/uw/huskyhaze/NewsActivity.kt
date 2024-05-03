package edu.tacoma.uw.huskyhaze

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
                    val newsAdapter = NewsAdapter(newsData.articles, object : NewsAdapter.OnItemClickListener {
                        override fun onItemClick(url: String) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(intent)
                        }
                    })

                    runOnUiThread {
                        val recyclerView = findViewById<RecyclerView>(R.id.news_recycler_view)
                        recyclerView.adapter = newsAdapter
                        recyclerView.layoutManager = LinearLayoutManager(this@NewsActivity)
                    }

                    Log.d("NewsResponse", newsData.toString())
                }
            } else {
                Log.e("NewsResponse", response.errorBody().toString())
            }
        }
    }
}