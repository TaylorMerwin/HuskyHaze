package edu.tacoma.uw.huskyhaze

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.tacoma.uw.huskyhaze.models.NewsData

class NewsAdapter(private val newsList: List<NewsData.ArticleData>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.headline_list_items, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = newsList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.text_title)
        private val sourceTextView: TextView = itemView.findViewById(R.id.text_source)
        private val imageView: ImageView = itemView.findViewById(R.id.img_headline)

        fun bind(article: NewsData.ArticleData) {
            titleTextView.text = article.title
            sourceTextView.text = article.description
            Picasso.get().load(article.urlToImage).into(imageView)
//             Glide.with(itemView.context).load(article.urlToImage).into(imageView)
        }
    }
}