/**
 * Team 3 - TCSS 450 - Spring 2024
 */
package edu.tacoma.uw.huskyhaze.models

class NewsData {

    data class NewsData(val articles: List<ArticleData>)

    data class ArticleData(
        val title: String,
        val description: String,
        val url: String,
        val urlToImage: String,
        val author: String
    )
}