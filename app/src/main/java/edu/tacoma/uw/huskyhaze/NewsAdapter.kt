/**
 * Team 3 - TCSS 450 - Spring 2024
 */
package edu.tacoma.uw.huskyhaze

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.tacoma.uw.huskyhaze.models.NewsData

/**
 * Adapter class to work with the recycler view in the news activity XML file.
 */
class NewsAdapter(
    newsList: List<NewsData.ArticleData>
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    /**
     * Removes any blank articles so they aren't displayed.
     */
    private val filteredNewsList = newsList.filter { it.title != "[Removed]" }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.headline_list_items, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = filteredNewsList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return filteredNewsList.size
    }

    /**
     * Initializes listeners for each article displayed on the screen.
     */
    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val article = filteredNewsList[position]
                val intent = Intent(v?.context, ArticleActivity::class.java).apply {
                    putExtra("articleUrl", article.url)
                }
                v?.context?.startActivity(intent)
            }
        }

        private val titleTextView: TextView = itemView.findViewById(R.id.text_title)
        private val sourceTextView: TextView = itemView.findViewById(R.id.text_source)
        private val imageView: ImageView = itemView.findViewById(R.id.img_headline)

        /**
         * Assigns article data to match their respective parts in the news XML format.
         */
        fun bind(article: NewsData.ArticleData) {
            titleTextView.text = article.title
            sourceTextView.text = article.author

            if (article.urlToImage != null) {
                Picasso.get().load(article.urlToImage).into(imageView)
            }
            else {
                Picasso.get().load(R.drawable.not_available).into(imageView)
            }
        }
    }
}