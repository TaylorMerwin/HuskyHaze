package edu.tacoma.uw.huskyhaze

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.tacoma.uw.huskyhaze.network.NewsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * Activity class for the news.
 */
class NewsActivity : AppCompatActivity(), View.OnClickListener {

    var b1: Button? = null
    var b2: Button? = null
    var b3: Button? = null
    var b4: Button? = null
    var b5: Button? = null
    var b6: Button? = null
    var b7: Button? = null

    /**
     * Initializes and sets a listener to all buttons and search bar.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        fetchNewsHeadlines()
        b1 = findViewById(R.id.btn_1)
        b1?.setOnClickListener(this)
        b2 = findViewById(R.id.btn_2)
        b2?.setOnClickListener(this)
        b3 = findViewById(R.id.btn_3)
        b3?.setOnClickListener(this)
        b4 = findViewById(R.id.btn_4)
        b4?.setOnClickListener(this)
        b5 = findViewById(R.id.btn_5)
        b5?.setOnClickListener(this)
        b6 = findViewById(R.id.btn_6)
        b6?.setOnClickListener(this)
        b7 = findViewById(R.id.btn_7)
        b7?.setOnClickListener(this)

        findViewById<SearchView>(R.id.search_view)?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(search: String): Boolean {
                fetchNewsSearch(search)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    /**
     * Fetches the news headlines when the news activity is first opened.
     */
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
                    val newsAdapter = NewsAdapter(newsData.articles)

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

    /**
     * Updates the displayed news articles if the user clicks on a category button.
     */
    override fun onClick(v: View?) {
        val button = v as Button
        val category = button.text.toString().lowercase(Locale.getDefault())
        fetchNewsByCategory(category)
    }

    private fun fetchNewsByCategory(category: String) {
        val newsService = NewsService.create()
        val apiKey = getString(R.string.news_api_key)
        val country = "us"

        CoroutineScope(Dispatchers.IO).launch {
            val response = newsService.getHeadlinesByCategory(country, category, apiKey)
            if (response.isSuccessful) {
                val newsData = response.body()
                if (newsData != null) {
                    runOnUiThread {
                        val recyclerView = findViewById<RecyclerView>(R.id.news_recycler_view)
                        recyclerView.adapter = NewsAdapter(newsData.articles)
                    }
                    Log.d("NewsResponse", newsData.toString())
                }
            } else {
                Log.e("NewsResponse", response.errorBody().toString())
            }
        }
    }

    /**
     * Updates the displayed news articles when the user enters in a keyword in the search bar.
     */
    private fun fetchNewsSearch(search: String) {
        val newsService = NewsService.create()
        val apiKey = getString(R.string.news_api_key)
        val country = "us"

        CoroutineScope(Dispatchers.IO).launch {
            val response = newsService.searchNews(search, apiKey)
            if (response.isSuccessful) {
                val newsData = response.body()
                if (newsData != null) {
                    runOnUiThread {
                        val recyclerView = findViewById<RecyclerView>(R.id.news_recycler_view)
                        recyclerView.adapter = NewsAdapter(newsData.articles)
                    }
                    Log.d("NewsResponse", newsData.toString())
                }
            } else {
                Log.e("NewsResponse", response.errorBody().toString())
            }
        }
    }
}