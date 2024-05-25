/**
 * Team 3 - TCSS 450 - Spring 2024
 */
package edu.tacoma.uw.huskyhaze

import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

/**
 * An activity to display an article the user clicked on into a web view.
 */
class ArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        /**
         * The URL of the article that is retrieved through the user's intent.
         */
        val articleUrl = intent.getStringExtra("articleUrl")

        fetchArticleContent(articleUrl)
    }

    /**
     * Displays the given article URL to the web view.
     * @param articleUrl The URL of the article to display.
     */
    private fun fetchArticleContent(articleUrl: String?) {
        if (articleUrl.isNullOrEmpty()) {
            return
        }

        val webView: WebView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
            }
        }
        webView.loadUrl(articleUrl)
    }
}